package com.github.johantiden.traktjava;


import com.fasterxml.jackson.core.type.TypeReference;
import com.github.johantiden.traktjava.cache.CacheClient;
import com.github.johantiden.traktjava.dto.AddHiddenMovieDto;
import com.github.johantiden.traktjava.dto.AddHiddenShowDto;
import com.github.johantiden.traktjava.dto.EpisodeDto;
import com.github.johantiden.traktjava.dto.HiddenMediaDto;
import com.github.johantiden.traktjava.dto.MarkEpisodeDto;
import com.github.johantiden.traktjava.dto.MarkMovieDto;
import com.github.johantiden.traktjava.dto.ShowProgressDto;
import com.github.johantiden.traktjava.dto.ShowSummaryDto;
import com.github.johantiden.traktjava.dto.StrangeInnerMovieDto;
import com.github.johantiden.traktjava.dto.WatchListMovieDto;
import com.github.johantiden.traktjava.dto.WatchedShowDto;
import com.github.johantiden.traktjava.internal.EpisodeId;
import com.github.johantiden.traktjava.internal.TraktHttpClient;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TraktTvClient {
    private static final Logger log = LoggerFactory.getLogger(TraktTvClient.class);
    public static final String RECOMMENDATIONS = "recommendations";
    public static final String PROGRESS_WATCHED = "progress_watched";

    private final TraktToken traktToken;
    private final CacheClient cacheClient;
    private final TraktHttpClient http;

    public TraktTvClient(TraktHttpClient http, CacheClient cacheClient, TraktToken traktToken) {
        this.http = Objects.requireNonNull(http);
        this.cacheClient = Objects.requireNonNull(cacheClient);
        this.traktToken = Objects.requireNonNull(traktToken);
    }


    public List<WatchedShowDto> getWatchedShows() {
        List<WatchedShowDto> dtos = http.get(
                                            "https://api.trakt.tv/sync/watched/shows?extended=noseasons",
                                            traktToken,
                                            new TypeReference<List<WatchedShowDto>>() {});

        List<WatchedShowDto> filtered = dtos.stream().filter(s -> !isShowHidden(s.show.ids.traktId)).collect(Collectors.toList());
        return filtered;
    }

    private boolean isShowHidden(int traktId) {

        List<HiddenMediaDto> hiddenShows = getHiddenShows();
        return hiddenShows.stream()
                .anyMatch(h -> h.show.ids.traktId == traktId);

    }

    private boolean isMovieHidden(int traktId) {

        List<HiddenMediaDto> hiddenShows = getHiddenMovies();
        return hiddenShows.stream()
                .anyMatch(h -> h.show.ids.traktId == traktId);

    }

    public List<HiddenMediaDto> getHiddenMovies() {
        String type = "movie";
        return getHidden(type, RECOMMENDATIONS, new TypeReference<List<HiddenMediaDto>>() {});
    }
    public List<HiddenMediaDto> getHiddenShows() {
        String type = "show";
        return getHidden(type, PROGRESS_WATCHED, new TypeReference<List<HiddenMediaDto>>() {});
    }

    private List<HiddenMediaDto> getHidden(String type, String category, TypeReference<List<HiddenMediaDto>> typeLiteral) {
        List<HiddenMediaDto> dtos = cacheClient.get(getHiddenCacheKey(type), () -> {
            return http.get(
                    "https://api.trakt.tv/users/hidden/" + category + "?type=" + type + "&limit=1000",
                    traktToken,
                    typeLiteral);
        });

        return dtos;
    }

    private String getHiddenCacheKey(String type) {
        return "hidden:"+type;
    }

    public ShowProgressDto getShowProgress(int traktId) {
        ShowProgressDto dto = http.get(
                                      "https://api.trakt.tv/shows/" + traktId + "/progress/watched?hidden=false&specials=true&count_specials=false&extended=full",
                                      traktToken,
                                      new TypeReference<ShowProgressDto>() {
                                      });
        return dto;
    }

    public void markEpisodeAsWatched(int traktId, int season, int number) {
        http.post("https://api.trakt.tv/scrobble/stop",
                 traktToken,
                 new MarkEpisodeDto(Instant.now(), new EpisodeId(getEpisode(traktId, season, number).ids.traktId)));

        cacheClient.delete(getShowCacheKey(traktId));
        cacheClient.delete(getEpisodeCacheKey(traktId, season, number));
    }

    public EpisodeDto getEpisode(int traktId, int season, int number) {
        return cacheClient.get("getEpisode/"+traktId+"/"+season+"/"+number, () -> {
            Preconditions.checkArgument(season >= 0);
            Preconditions.checkArgument(number > 0);
            try {
                EpisodeDto dto = http.get(
                                         "https://api.trakt.tv/shows/" + traktId + "/seasons/" + season + "/episodes/" + number + "?extended=full",
                                         traktToken,
                                         new TypeReference<EpisodeDto>() {
                                         });
                return dto;
            } catch (RuntimeException e) {
                log.error("Could not get episode data.", e);
                throw new RuntimeException(e);
            }
        });
    }

    public ShowSummaryDto getShow(int traktId) {
        ShowSummaryDto dto = http.get(
                "https://api.trakt.tv/shows/" + traktId + "?extended=full",
                traktToken,
                new TypeReference<ShowSummaryDto>() {
                });
        return dto;
    }

    public void hideShow(int traktId) {

        http.post("https://api.trakt.tv/users/hidden/"+PROGRESS_WATCHED,
                traktToken,
                new AddHiddenShowDto(traktId));

        cacheClient.delete(getHiddenCacheKey("show"));
        cacheClient.delete(getShowCacheKey(traktId));

    }

    public List<EpisodeDto> getSeason(int traktId, int season) {
        List<EpisodeDto> dto = http.get(
                "https://api.trakt.tv/shows/" + traktId + "/seasons/"+season+"?extended=full",
                traktToken,
                new TypeReference<List<EpisodeDto>>() {
                });
        return dto;
    }

    private static String getShowCacheKey(int traktId) {
        return "show/"+traktId;
    }

    private static String getEpisodeCacheKey(int traktId, int season, int number) {
        return "episode/"+ traktId +"/"+season+"/"+number;
    }

    public void hideMovie(int traktId) {
        http.post("https://api.trakt.tv/users/hidden/" + RECOMMENDATIONS,
                traktToken,
                new AddHiddenMovieDto(traktId));

        http.delete("https://api.trakt.tv/recommendations/movies/" + traktId,
                traktToken);


        cacheClient.delete(getHiddenCacheKey("movie"));
        cacheClient.delete(getShowCacheKey(traktId));

    }

    public List<StrangeInnerMovieDto> getWatchlistMovies() {
        List<WatchListMovieDto> dtos = http.get(
                "https://api.trakt.tv/sync/watchlist/movies",
                traktToken,
                new TypeReference<List<WatchListMovieDto>>() {});

        List<StrangeInnerMovieDto> filtered = dtos.stream()
                .filter(s -> !isMovieHidden(s.movie.ids.traktId))
                .map(dto -> dto.movie)
                .collect(Collectors.toList());
        return filtered;
    }

    public void markMovieAsWatched(int traktId) {
        http.post("https://api.trakt.tv/scrobble/stop",
                traktToken,
                new MarkMovieDto(Instant.now(), traktId));

        log.error("TODO: CLEAR CACHE!");
    }

    public List<StrangeInnerMovieDto> getMovieRecommendations() {
        List<StrangeInnerMovieDto> dtos = http.get(
                "https://api.trakt.tv/recommendations/movies?limit=100",
                traktToken,
                new TypeReference<List<StrangeInnerMovieDto>>() {});

        List<StrangeInnerMovieDto> filtered = dtos.stream()
                .filter(m -> !isMovieHidden(m.ids.traktId))
                .collect(Collectors.toList());
        return filtered;
    }
}
