package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EpisodeDto {

    public final int season;
    public final int number;
    public final String title;
    public final IdsDto ids;
    public final String overView;
    public final Date firstAired;

    @JsonCreator
    public EpisodeDto(
            @JsonProperty("season") int season,
            @JsonProperty("number") int number,
            @JsonProperty("title") String title,
            @JsonProperty("ids") IdsDto ids,
            @JsonProperty("number_abs") Integer numberAbs,
            @JsonProperty("overView") String overView,
            @JsonProperty("first_aired") Date firstAired,
            @JsonProperty("updated_at") Date updatedAt,
            @JsonProperty("rating") int rating,
            @JsonProperty("votes") int votes

    ) {
        this.season = season;
        this.number = number;
        this.title = title;
        this.ids = ids;
        this.overView = overView;
        this.firstAired = firstAired;
    }
}
