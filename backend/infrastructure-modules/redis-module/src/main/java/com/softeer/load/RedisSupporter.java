package com.softeer.load;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RedisSupporter {

    public <T> T loadObjectWithFallback(Loader<T> redisLoader, Loader<T> fallbackLoader) {
        try {
            T result = redisLoader.load();
            if (result == null) {
                log.warn("[RedisSupporter] Redis returned empty list. Falling back to adapter.");
                throw new RuntimeException("Redis returned null result");
            }
            return result;
        } catch (Exception e) {
            log.warn("[RedisSupporter] Failed to load from Redis : {}. Falling back to adapter.", e.getMessage());
            return fallbackLoader.load();
        }
    }

    public <T> List<T> loadListWithFallback(Loader<List<T>> redisLoader, Loader<List<T>> fallbackLoader) {
        try {
            List<T> result = redisLoader.load();
            if (result == null || result.isEmpty()) {
                log.warn("[RedisSupporter] Redis returned empty list. Falling back to adapter.");
                throw new RuntimeException("Redis returned empty list");
            }
            return result;
        } catch (Exception e) {
            log.warn("[RedisSupporter] Failed to load list from Redis : {}. Falling back to adapter.", e.getMessage());
            return fallbackLoader.load();
        }
    }

    public <T, R> Map<T, R> loadMapWithFallback(Loader<Map<T, R>> redisLoader, Loader<Map<T, R>> fallbackLoader) {
        try {
            Map<T, R> result = redisLoader.load();
            if (result == null || result.isEmpty()) {
                log.warn("[RedisSupporter] Redis returned empty list. Falling back to adapter.");
                log.warn("[RedisSupporter] Redis returned empty map. Falling back to adapter.");
                throw new RuntimeException("Redis returned empty map");
            }
            return result;
        } catch (Exception e) {
            log.warn("[RedisSupporter] Failed to load list from Redis : {}. Falling back to adapter.", e.getMessage());
            return fallbackLoader.load();
        }
    }
}
