package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class HiddenMediaDto {

    public final Date hiddenAt;
    public final String type;
    public final InnerMediaDto show;
    public final InnerMediaDto movie;

    @JsonCreator
    public HiddenMediaDto(
            @JsonProperty("hidden_at") Date hiddenAt,
            @JsonProperty("type") String type,
            @JsonProperty("show") InnerMediaDto show,
            @JsonProperty("movie") InnerMediaDto movie
    ) {
        this.hiddenAt = hiddenAt;
        this.type = type;
        this.show = show;
        this.movie = movie;
    }
    
    public static class InnerMediaDto {
        public final String title;
        public final int year;
        public final IdsDto ids;

        @JsonCreator
        public InnerMediaDto(
                @JsonProperty("title") String title,
                @JsonProperty("year") int year,
                @JsonProperty("ids") IdsDto ids
        ) {
            this.title = title;
            this.year = year;
            this.ids = ids;
        }
    }

}
