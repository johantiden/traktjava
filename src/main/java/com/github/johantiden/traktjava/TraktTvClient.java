package com.github.johantiden.traktjava;


import com.fasterxml.jackson.core.type.TypeReference;
import com.github.johantiden.traktjava.cache.CacheClient;
import com.github.johantiden.traktjava.dto.AddHiddenShowDto;
import com.github.johantiden.traktjava.dto.EpisodeDto;
import com.github.johantiden.traktjava.dto.HiddenShowDto;
import com.github.johantiden.traktjava.dto.MarkDto;
import com.github.johantiden.traktjava.dto.ShowProgressDto;
import com.github.johantiden.traktjava.dto.ShowSummaryDto;
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

        List<WatchedShowDto> filtered = dtos.stream().filter(s -> !isHidden(s.show.ids.traktId)).collect(Collectors.toList());
        return filtered;
    }

    private boolean isHidden(int traktId) {

        List<HiddenShowDto> hiddenShows = getHiddenShows();
        return hiddenShows.stream()
                .anyMatch(h -> h.show.ids.traktId == traktId);

    }

    public List<HiddenShowDto> getHiddenShows() {
        List<HiddenShowDto> dtos = cacheClient.get("hiddenShows", () -> http.get(
                "https://api.trakt.tv/users/hidden/progress_watched?type=show&limit=1000",
                traktToken,
                new TypeReference<List<HiddenShowDto>>() {}));

        return dtos;
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
                 new MarkDto(Instant.now(), new EpisodeId(getEpisode(traktId, season, number).ids.traktId)));

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

        http.post("https://api.trakt.tv/users/hidden/progress_watched",
                traktToken,
                new AddHiddenShowDto(traktId));

        cacheClient.delete("hiddenShows");
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
}
