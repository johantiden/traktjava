package com.github.johantiden.traktjava.dto;

import com.google.common.collect.Lists;

import java.util.List;

public class AddShowToWatchlistDto {

    public final List<SendShowDto> shows;

    public AddShowToWatchlistDto(int traktId) {
        shows = Lists.newArrayList(new SendShowDto(traktId));
    }

    public List<SendShowDto> getShows() {
        return shows;
    }
}
