package com.github.johantiden.traktjava.dto;

import com.google.common.collect.Lists;

import java.util.List;

public class AddShowToWatchlistDto {

    public final List<SendItemDto> shows;

    public AddShowToWatchlistDto(int traktId) {
        shows = Lists.newArrayList(new SendItemDto(traktId));
    }

    public List<SendItemDto> getShows() {
        return shows;
    }
}
