package com.github.johantiden.traktjava.dto;

public class SendShowDto {
    private final SendShowIdDto ids;

    SendShowDto(int showTraktId) {
        this.ids = new SendShowIdDto(showTraktId);
    }

    public SendShowIdDto getIds() {
        return ids;
    }

    private static class SendShowIdDto {
        private final int trakt;

        private SendShowIdDto(int trakt) {
            this.trakt = trakt;
        }

        public int getTrakt() {
            return trakt;
        }
    }
}
