package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FullMovieDto {

    public final String title;
    public final int year;
    public final IdsDto ids;
    public final String tagline;
    public final String overview;
    public final Date releaseDate;
    public final int runtimeMinutes;
    public final String trailerUrl;
    public final BigDecimal rating;
    public final List<String> genres;


    @JsonCreator
    public FullMovieDto(
            @JsonProperty("title") String title,
            @JsonProperty("year") int year,
            @JsonProperty("ids") IdsDto ids,
            @JsonProperty("tagline") String tagline,
            @JsonProperty("overview") String overview,
            @JsonProperty("released") Date releaseDate,
            @JsonProperty("runtime") int runtimeMinutes,
            @JsonProperty("trailer") String trailerUrl,
            @JsonProperty("rating") BigDecimal rating,
            @JsonProperty("genres") List<String> genres
    ) {
        this.title = title;
        this.year = year;
        this.ids = ids;
        this.tagline = tagline;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.runtimeMinutes = runtimeMinutes;
        this.trailerUrl = trailerUrl;
        this.rating = rating;
        this.genres = genres;
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

    public String getTagline() {
        return tagline;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public List<String> getGenres() {
        return genres;
    }
}
