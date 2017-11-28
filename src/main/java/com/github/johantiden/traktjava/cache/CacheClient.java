package com.github.johantiden.traktjava.cache;

import java.util.function.Supplier;

public interface CacheClient {
    <T> T get(String cacheKey, Supplier<T> supplier);

    void delete(String cacheKey);

    <T> void set(String cacheKey, T object);
}
