package com.github.johantiden.traktjava.dto;

public class SendItemDto {
    private final SendItemIdDto ids;

    SendItemDto(int showTraktId) {
        this.ids = new SendItemIdDto(showTraktId);
    }

    public SendItemIdDto getIds() {
        return ids;
    }

    private static class SendItemIdDto {
        private final int trakt;

        private SendItemIdDto(int trakt) {
            this.trakt = trakt;
        }

        public int getTrakt() {
            return trakt;
        }
    }
}
