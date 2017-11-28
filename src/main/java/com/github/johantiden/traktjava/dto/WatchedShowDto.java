package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchedShowDto {

    /*

    {
    "listed_at": "2014-09-01T09:10:11.000Z",
    "type": "show",
    "show": {
      "title": "Breaking Bad",
      "year": 2008,
      "ids": {
        "trakt": 1,
        "slug": "breaking-bad",
        "tvdb": 81189,
        "imdb": "tt0903747",
        "tmdb": 1396,
        "tvrage": 18164
      }
    }
  }


     */


    public final StrangeInnerShowDto show;

    @JsonCreator
    public WatchedShowDto(
            @JsonProperty("show") StrangeInnerShowDto show,
            @JsonProperty("type") String type // "show"
    ) {
        this.show = show;
    }

}
