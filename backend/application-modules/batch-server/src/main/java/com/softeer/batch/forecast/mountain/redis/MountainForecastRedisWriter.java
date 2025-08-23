package com.softeer.batch.forecast.mountain.redis;

import com.softeer.batch.forecast.chained.context.ExecutionContextKeys;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureKey;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureValue;
import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.entity.ForecastRedisEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.mapper.RecordMapper;
import com.softeer.scan.RedisKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class MountainForecastRedisWriter {

    private static final String PREFIX = "forecast";

    private final RedisKeyGenerator redisKeyGenerator;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RecordMapper recordMapper;
    private final Map<DailyTemperatureKey, DailyTemperatureValue> dailyTempMap;

    public MountainForecastRedisWriter(
            RedisKeyGenerator redisKeyGenerator,
            RedisTemplate<String, Object> redisTemplate,
            RecordMapper recordMapper,
            @Value("#{stepExecutionContext['" + ExecutionContextKeys.DAILY_TEMP_MAP + "']}")
            Map<DailyTemperatureKey, DailyTemperatureValue> dailyTempMap
    ) {
        this.redisKeyGenerator = redisKeyGenerator;
        this.redisTemplate = redisTemplate;
        this.recordMapper = recordMapper;
        this.dailyTempMap = dailyTempMap;
    }

    public void pipelineUpdateMountainForecast(List<? extends MountainDailyForecast> items) {
        try {
            Map<byte[], Map<byte[], byte[]>> bulkData = prepareBulkData(items);
            executePipelinedOperations(bulkData);
            log.info("Successfully updated {} mountain forecast items to Redis", bulkData.size());
        } catch (Exception e) {
            log.error("Failed to batch update Redis forecast", e);
            throw new RuntimeException("Redis batch update failed", e);
        }
    }

    private Map<byte[], Map<byte[], byte[]>> prepareBulkData(List<? extends MountainDailyForecast> items) {
        Map<byte[], Map<byte[], byte[]>> bulkData = new java.util.HashMap<>();

        items.forEach(item ->
            item.hourlyForecasts().forEach(forecast -> {
                try {

                    byte[] key = redisKeyGenerator.serializeKey(PREFIX, item.gridId(), ForecastType.MOUNTAIN, forecast.dateTime());
                    DailyTemperatureValue dailyTemperatureValue = dailyTempMap.get(new DailyTemperatureKey(item.gridId(), forecast.dateTime().toLocalDate()));

                    if (dailyTemperatureValue == null) return;

                    ForecastRedisEntity forecastRedisEntity = new ForecastRedisEntity(
                            forecast,
                            dailyTemperatureValue.highestTemperature(),
                            dailyTemperatureValue.lowestTemperature(),
                            item.gridId()
                    );

                    Map<byte[], byte[]> value = recordMapper.toByteMap(forecastRedisEntity);

                    bulkData.put(key, value);
                } catch (Exception e) {
                    log.error("Failed to prepare Redis data for grid ({}, {}) at {} {}", PREFIX, item.gridId(), ForecastType.SHORT, forecast.dateTime());
                }
            })
        );

        return bulkData;
    }

    private void executePipelinedOperations(Map<byte[], Map<byte[], byte[]>> bulkData) {
        redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            bulkData.forEach((key, value) -> {
                try {
                    connection.hashCommands().hMSet(key, value);
                } catch (Exception e) {
                    log.warn("Failed to execute pipelined operation for key: {}, skipping this item", new String(key), e);
                }
            });
            return null;
        });
    }
}
