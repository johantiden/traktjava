package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ShowSummaryDto {

    public final IdsDto ids;
    public final String title;
    public final String status;

    @JsonCreator
    public ShowSummaryDto(
            @JsonProperty("ids") IdsDto ids,
            @JsonProperty("title") String title,
            @JsonProperty("year") int year,
            @JsonProperty("overview") String overview,
            @JsonProperty("first_aired") Date firstAired,
            @JsonProperty("airs") AirsDto airs,
            @JsonProperty("runtime") int runtimeMinutes,
            @JsonProperty("trailer") String trailer,
            @JsonProperty("homepage") String homepage,
            @JsonProperty("status") String status,
            @JsonProperty("rating") int rating,
            @JsonProperty("votes") int votes,
            @JsonProperty("genres") List<String> genres
    ) {
        this.ids = ids;
        this.title = title;
        this.status = status;
    }

    public static class AirsDto {
        @JsonCreator
        public AirsDto(
                @JsonProperty("day") String weekDay,
                @JsonProperty("time") String time,
                @JsonProperty("timezone") String timeZone
        ) {
        }
    }
}
