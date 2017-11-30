package com.github.johantiden.traktjava.dto;

import com.google.common.collect.Lists;

import java.util.List;

public class AddHiddenMovieDto {

    public List<SendShowDto> shows;

    public AddHiddenMovieDto(int traktId) {
        shows = Lists.newArrayList(new SendShowDto(traktId));
    }

    public List<SendShowDto> getShows() {
        return shows;
    }
}
