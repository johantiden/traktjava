package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StrangeInnerMovieDto {

    public final String title;
    public final int year;
    public final IdsDto ids;


    @JsonCreator
    public StrangeInnerMovieDto(
            @JsonProperty("title") String title,
            @JsonProperty("year") int year,
            @JsonProperty("ids") IdsDto ids
    ) {
        this.title = title;
        this.year = year;
        this.ids = ids;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public IdsDto getIds() {
        return ids;
    }
}
