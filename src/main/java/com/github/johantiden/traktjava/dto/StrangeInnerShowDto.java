package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class StrangeInnerShowDto {
    public final String title;
    public final int year;
    public final IdsDto ids;
    public final String overview;
    public final Date firstAired;
    public final int airedEpisodes;

    @JsonCreator
    public StrangeInnerShowDto(
            @JsonProperty("title") String title,
            @JsonProperty("year") int year,
            @JsonProperty("ids") IdsDto ids,
            @JsonProperty("overview") String overview,
            @JsonProperty("first_aired") Date firstAired,
            @JsonProperty("airs") Object airs,
            @JsonProperty("runtime") int runtime,
            @JsonProperty("trailer") String trailerUrl,
            @JsonProperty("status") String status, // "returing series"
            @JsonProperty("aired_episodes") int airedEpisodes
    ) {

        this.title = title;
        this.year = year;
        this.ids = ids;
        this.overview = overview;
        this.firstAired = firstAired;
        this.airedEpisodes = airedEpisodes;
    }
}
