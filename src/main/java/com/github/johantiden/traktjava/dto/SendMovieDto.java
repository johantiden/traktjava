package com.github.johantiden.traktjava.dto;

public class SendMovieDto {
    private final SendMovieIdDto ids;

    SendMovieDto(int showTraktId) {
        this.ids = new SendMovieIdDto(showTraktId);
    }

    public SendMovieIdDto getIds() {
        return ids;
    }

    private static class SendMovieIdDto {
        private final int trakt;

        private SendMovieIdDto(int trakt) {
            this.trakt = trakt;
        }

        public int getTrakt() {
            return trakt;
        }
    }
}
