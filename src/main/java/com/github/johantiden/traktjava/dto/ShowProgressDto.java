package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ShowProgressDto {


    /*
    HEADERS
Content-Type:application/json
BODY
{
  "aired": 8,
  "completed": 6,
  "last_watched_at": "2015-03-21T19:03:58.000Z",
  "seasons": [
    {
      "number": 1,
      "aired": 8,
      "completed": 6,
      "episodes": [
        {
          "number": 1,
          "completed": true,
          "last_watched_at": "2015-03-21T19:03:58.000Z"
        },
        {
          "number": 2,
          "completed": true,
          "last_watched_at": "2015-03-21T19:03:58.000Z"
        },
        {
          "number": 3,
          "completed": true,
          "last_watched_at": "2015-03-21T19:03:58.000Z"
        },
        {
          "number": 4,
          "completed": true,
          "last_watched_at": "2015-03-21T19:03:58.000Z"
        },
        {
          "number": 5,
          "completed": true,
          "last_watched_at": "2015-03-21T19:03:58.000Z"
        },
        {
          "number": 6,
          "completed": true,
          "last_watched_at": "2015-03-21T19:03:58.000Z"
        },
        {
          "number": 7,
          "completed": false,
          "last_watched_at": null
        },
        {
          "number": 8,
          "completed": false,
          "last_watched_at": null
        }
      ]
    }
  ],
  "hidden_seasons": [
    {
      "number": 2,
      "ids": {
        "trakt": 3051,
        "tvdb": 498968,
        "tmdb": 53334,
        "tvrage": null
      }
    }
  ],
  "next_episode": {
    "season": 1,
    "number": 7,
    "title": "Water",
    "ids": {
      "trakt": 62315,
      "tvdb": 4849873,
      "imdb": null,
      "tmdb": null,
      "tvrage": null
    }
  }
}
     */


    public final int aired;
    public final int completed;
    public final Date lastWatchedAt;
    public final NextEpisodeDto nextEpisodeDto;
    public final List<ShowProgressSeasonDto> seasons;

    @JsonCreator
    public ShowProgressDto(
            @JsonProperty("aired") int aired,
            @JsonProperty("completed") int completed,
            @JsonProperty("last_watched_at") Date lastWatchedAt,
            @JsonProperty("next_episode") NextEpisodeDto nextEpisodeDto,
            @JsonProperty("seasons") List<ShowProgressSeasonDto> seasons
    ) {
        this.aired = aired;
        this.completed = completed;
        this.lastWatchedAt = lastWatchedAt;
        this.nextEpisodeDto = nextEpisodeDto;
        this.seasons = seasons;
    }

    public static class NextEpisodeDto {
        public final int season;
        public final int number;
        public final String title;
        public final IdsDto idsDto;

        @JsonCreator
        public NextEpisodeDto(
                @JsonProperty("season") int season,
                @JsonProperty("number") int number,
                @JsonProperty("title") String title,
                @JsonProperty("ids") IdsDto idsDto
        ) {
            this.season = season;
            this.number = number;
            this.title = title;
            this.idsDto = idsDto;
        }
    }

    public static class ShowProgressSeasonDto {
        public final int seasonNumber;
        public final int aired;
        public final int completed;
        public final List<ShowProgressEpisodeDto> episodes;

        @JsonCreator
        public ShowProgressSeasonDto(
                @JsonProperty("number") int seasonNumber,
                @JsonProperty("aired") int aired,
                @JsonProperty("completed") int completed,
                @JsonProperty("episodes") List<ShowProgressEpisodeDto> episodes
        ) {
            this.seasonNumber = seasonNumber;
            this.aired = aired;
            this.completed = completed;
            this.episodes = episodes;
        }

        public int getSeasonNumber() {
            return seasonNumber;
        }
    }

    public static class ShowProgressEpisodeDto {
        public final int episodeNumber;
        public final boolean completed;
        public final Date lastWatchedAt;

        @JsonCreator
        public ShowProgressEpisodeDto(
                @JsonProperty("number") int episodeNumber,
                @JsonProperty("completed") boolean completed,
                @JsonProperty("last_watched_at") Date lastWatchedAt) {

            this.episodeNumber = episodeNumber;
            this.completed = completed;
            this.lastWatchedAt = lastWatchedAt;
        }
    }
}
