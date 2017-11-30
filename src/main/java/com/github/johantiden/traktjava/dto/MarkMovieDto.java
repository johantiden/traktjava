package com.github.johantiden.traktjava.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MarkMovieDto {

    private final SendMovieDto episode;
    private final int progress = 100;
    private final String app_version = "1.0";
    private final String app_date;

    public MarkMovieDto(Instant instant, int movieTraktId) {
        this.app_date = DateTimeFormatter.ofPattern("YYYY-MM-dd").withZone(ZoneId.systemDefault()).format(instant);
        this.episode = new SendMovieDto(movieTraktId);
    }

    public SendMovieDto getEpisode() {
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
