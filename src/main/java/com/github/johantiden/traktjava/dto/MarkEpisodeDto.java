package com.github.johantiden.traktjava.dto;

import com.github.johantiden.traktjava.internal.EpisodeId;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MarkEpisodeDto {

    private final SendEpisodeDto episode;
    private final int progress = 100;
    private final String app_version = "1.0";
    private final String app_date;

    public MarkEpisodeDto(Instant instant, EpisodeId id) {
        this.app_date = DateTimeFormatter.ofPattern("YYYY-MM-dd").withZone(ZoneId.systemDefault()).format(instant);
        this.episode = new SendEpisodeDto(id.id);
    }

    public SendEpisodeDto getEpisode() {
        return episode;
    }

    public int getProgress() {
        return progress;
    }

    public String getApp_version() {
        return app_version;
    }

    public String getApp_date() {
        return app_date;
    }

}
