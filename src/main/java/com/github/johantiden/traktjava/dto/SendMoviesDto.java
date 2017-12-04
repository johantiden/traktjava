package com.github.johantiden.traktjava.dto;

import com.google.common.collect.Lists;

import java.util.List;

public class SendMoviesDto {

    public List<SendItemDto> movies;

    public SendMoviesDto(int traktId) {
        movies = Lists.newArrayList(new SendItemDto(traktId));
    }

    public List<SendItemDto> getMovies() {
        return movies;
    }
}
