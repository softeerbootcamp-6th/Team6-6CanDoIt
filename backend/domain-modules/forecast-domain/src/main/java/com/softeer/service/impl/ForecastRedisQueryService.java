package com.softeer.service.impl;

import com.softeer.domain.Forecast;
import com.softeer.entity.ForecastRedisEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.scan.RedisDataLoader;
import com.softeer.scan.RedisKeyGenerator;
import com.softeer.scan.RedisKeyScanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForecastRedisQueryService {

    private static final String PREFIX = "forecast";

    private final RedisKeyGenerator redisKeyGenerator;
    private final RedisKeyScanner redisKeyScanner;
    private final RedisDataLoader redisDataLoader;

    public List<Forecast> findShortForecasts(int gridId) {
        String pattern = redisKeyGenerator.generateScanPattern(PREFIX, gridId, ForecastType.SHORT);
        List<String> keys = redisKeyScanner.scanKeys(pattern);

        return redisDataLoader.loadEntities(keys, ForecastRedisEntity.class)
                .stream()
                .map(ForecastRedisEntity::toDomain)
                .toList();
    }

    public Forecast findForecastByGridIdAndTypeAndDateTime(int gridId, ForecastType forecastType, LocalDateTime dateTime) {
        String key = redisKeyGenerator.generateKey(PREFIX, gridId, forecastType, dateTime);

        return ForecastRedisEntity.toDomain(redisDataLoader.loadEntity(key, ForecastRedisEntity.class));
    }


    public Map<Integer, Forecast> findForecastsByGridIdAndTypeAndDateTime(List<Integer> gridIds, ForecastType forecastType, LocalDateTime dateTime) {
        List<String> keys = gridIds.stream()
                .map(gridId -> redisKeyGenerator.generateKey(PREFIX, gridId, forecastType, dateTime))
                .toList();

        List<ForecastRedisEntity> forecastRedisEntities = redisDataLoader.loadEntities(keys, ForecastRedisEntity.class)
                .stream()
                .toList();

        return forecastRedisEntities.stream()
                .collect(
                        Collectors.toMap(
                                ForecastRedisEntity::gridId,
                                ForecastRedisEntity::toDomain
                        )
                );
    }
}

