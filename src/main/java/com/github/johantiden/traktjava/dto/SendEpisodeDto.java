package com.github.johantiden.traktjava.dto;

public class SendEpisodeDto {
    private final SendEpisodeIdDto ids;

    SendEpisodeDto(int episodeTraktId) {
        this.ids = new SendEpisodeIdDto(episodeTraktId);
    }

    public SendEpisodeIdDto getIds() {
        return ids;
    }

    private static class SendEpisodeIdDto {
        private final int trakt;

        private SendEpisodeIdDto(int trakt) {
            this.trakt = trakt;
        }

        public int getTrakt() {
            return trakt;
        }
    }
}
