package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class IdsDto {
    public final int traktId;
    public final String slug;
    public final Optional<String> imdb;

    /*
    "trakt": 1,
        "slug": "breaking-bad",
        "tvdb": 81189,
        "imdb": "tt0903747",
        "tmdb": 1396,
        "tvrage": 18164
     */

    @JsonCreator
    public IdsDto(
            @JsonProperty("trakt") int traktId,
            @JsonProperty("slug") String slug,
            @JsonProperty("imdb") String imdb
    ) {
        this.traktId = traktId;
        this.slug = slug;
        this.imdb = Optional.ofNullable(imdb);
        boolean a = true;
    }
}
