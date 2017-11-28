package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class HiddenShowDto {

    public final Date hiddenAt;
    public final String type;
    public final InnerShowDto show;

    @JsonCreator
    public HiddenShowDto(
            @JsonProperty("hidden_at") Date hiddenAt,
            @JsonProperty("type") String type,
            @JsonProperty("show") InnerShowDto show
    ) {
        this.hiddenAt = hiddenAt;
        this.type = type;
        this.show = show;
    }
    
    public static class InnerShowDto {
        public final String title;
        public final int year;
        public final IdsDto ids;

        @JsonCreator
        public InnerShowDto(
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
