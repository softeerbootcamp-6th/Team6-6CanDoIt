package com.softeer.batch.forecast.shortterm.redis;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.entity.enums.ForecastType;
import com.softeer.mapper.RecordMapper;
import com.softeer.scan.RedisKeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ShortForecastRedisWriter {

    private static final String PREFIX = "forecast";

    private final RedisKeyGenerator redisKeyGenerator;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RecordMapper recordMapper;

    public void pipelineUpdateShortForecast(List<? extends ShortForecastList> items) {
        try {
            Map<byte[], Map<byte[], byte[]>> bulkData = prepareBulkData(items);
            executePipelinedOperations(bulkData);
            log.info("Successfully updated {} short forecast items to Redis", bulkData.size());
        } catch (Exception e) {
            log.error("Failed to batch update Redis forecast", e);
            throw new RuntimeException("Redis batch update failed", e);
        }
    }

    private Map<byte[], Map<byte[], byte[]>> prepareBulkData(List<? extends ShortForecastList> items) {
        Map<byte[], Map<byte[], byte[]>> bulkData = new java.util.HashMap<>();

        items.forEach(item -> {
            item.forecasts().forEach(forecast -> {
                try {
                    byte[] key = redisKeyGenerator.serializeKey(PREFIX, item.gridId(), ForecastType.SHORT, forecast.dateTime());
                    Map<byte[], byte[]> value = recordMapper.toByteMap(forecast);
                    bulkData.put(key, value);

                } catch (Exception e) {
                    log.error("Failed to prepare Redis data for grid ({}, {}) at {} {}", PREFIX, item.gridId(), ForecastType.SHORT, forecast.dateTime());
                }
            });
        });

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
