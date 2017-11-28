package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SeasonDto {

    public final int number;
    public final List<EpisodeDto> episodes;

    @JsonCreator
    public SeasonDto(
            @JsonProperty("number") int number,
            @JsonProperty("episodes") List<EpisodeDto> episodes
    ) {
        this.number = number;
        this.episodes = episodes;
    }

}
