package com.github.johantiden.traktjava.cache;

import java.util.function.Supplier;

public class NoCacheCacheClient implements CacheClient {
    @Override
    public <T> T get(String cacheKey, Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public void delete(String cacheKey) {

    }

    @Override
    public <T> void set(String cacheKey, T object) {

    }
}
