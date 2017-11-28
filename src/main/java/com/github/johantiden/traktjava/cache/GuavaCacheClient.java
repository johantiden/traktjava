package com.github.johantiden.traktjava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class GuavaCacheClient implements CacheClient {

    private final Cache<String, Optional<Object>> cache;


    public GuavaCacheClient(Duration ttl) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(ttl.toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public <T> T get(String cacheKey, Supplier<T> supplier) {
        try {
            //noinspection unchecked
            return (T)cache.get(cacheKey, () -> {
                T t = supplier.get();
                return Optional.ofNullable(t);
            }).orElse(null);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String cacheKey) {
        cache.invalidate(cacheKey);
    }

    @Override
    public <T> void set(String cacheKey, T object) {
        cache.put(cacheKey, Optional.ofNullable(object));
    }
}
