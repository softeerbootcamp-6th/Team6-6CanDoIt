package com.softeer.batch.forecast.ultra.redis;

import com.softeer.batch.forecast.ultra.dto.UltraForecastResponse;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.entity.enums.ForecastType;
import com.softeer.mapper.RecordMapper;
import com.softeer.scan.RedisKeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class UltraForecastRedisWriter {

    private static final String PREFIX = "forecast";

    private final RedisKeyGenerator redisKeyGenerator;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RecordMapper recordMapper;

    public void pipelineUpdateUltraForecast(List<? extends UltraForecastResponseList> items) {
        try {
            Map<byte[], Map<byte[], byte[]>> bulkData = prepareBulkData(items);
            executePipelinedOperations(bulkData);
            log.info("Successfully updated {} forecast items to Redis", bulkData.size());
        } catch (Exception e) {
            log.error("Failed to batch update Redis forecast", e);
            throw new RuntimeException("Redis batch update failed", e);
        }
    }

    public void updateUltraForecast(List<? extends UltraForecastResponseList> items) {
        try {
            Map<String, Map<String, Object>> bulkData = prepareStringBulkData(items);
            bulkData.forEach((key, value) -> {
                redisTemplate.opsForHash().putAll(key, value);
            });
            log.info("Successfully updated {} forecast items to Redis", bulkData.size());
        } catch (Exception e) {
            log.error("Failed to batch update Redis forecast", e);
            throw new RuntimeException("Redis batch update failed", e);
        }
    }

    private Map<byte[], Map<byte[], byte[]>> prepareBulkData(List<? extends UltraForecastResponseList> items) {
        Map<byte[], Map<byte[], byte[]>> bulkData = new HashMap<>();

        items.forEach(item -> {
            for (UltraForecastResponse response : item.response()) {
                try {
                    UltraForecastRedisEntity redisEntity = new UltraForecastRedisEntity(response);

                    byte[] key = redisKeyGenerator.serializeKey(PREFIX, response.gridId(), ForecastType.SHORT, response.dateTime());
                    Map<byte[], byte[]> value = recordMapper.toByteMap(redisEntity);
                    bulkData.put(key, value);

                } catch (Exception e) {
                    log.warn("Failed to prepare data for gridId: {}, skipping this item", response.gridId(), e);
                }
            }
        });

        return bulkData;
    }

    private Map<String, Map<String, Object>> prepareStringBulkData(List<? extends UltraForecastResponseList> items) {
        Map<String, Map<String, Object>> bulkData = new HashMap<>();

        items.forEach(item -> {
            for (UltraForecastResponse response : item.response()) {
                try {
                    UltraForecastRedisEntity redisEntity = new UltraForecastRedisEntity(response);

                    String key = redisKeyGenerator.generateKey(PREFIX, response.gridId(), ForecastType.SHORT, response.dateTime());
                    Map<String, Object> value = recordMapper.toMap(redisEntity);
                    bulkData.put(key, value);

                } catch (Exception e) {
                    log.warn("Failed to prepare data for gridId: {}, skipping this item", response.gridId(), e);
                }
            }
        });

        return bulkData;
    }

    private void executePipelinedOperations(Map<byte[], Map<byte[], byte[]>> bulkData) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            bulkData.forEach((key, value) -> {
                try {
                    connection.hashCommands().hMSet(key, value);
                } catch (Exception e) {
                    log.warn("Failed to execute Redis operation for key: {}", key, e);
                }
            });
            return null;
        });
    }
}