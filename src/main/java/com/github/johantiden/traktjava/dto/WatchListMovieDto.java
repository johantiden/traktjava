package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchListMovieDto {


    public final StrangeInnerMovieDto movie;

    @JsonCreator
    public WatchListMovieDto(
            @JsonProperty("movie") StrangeInnerMovieDto movie,
            @JsonProperty("type") String type // "show"
    ) {
        this.movie = movie;
    }

}
