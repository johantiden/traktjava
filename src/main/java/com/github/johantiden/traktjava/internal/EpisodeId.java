package com.github.johantiden.traktjava.internal;

public class EpisodeId {
    public int id;

    public EpisodeId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EpisodeId{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpisodeId episodeId = (EpisodeId) o;

        return id == episodeId.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
