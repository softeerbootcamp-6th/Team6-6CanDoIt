package com.softeer.batch.forecast.shortterm.redis;

import com.softeer.batch.common.dto.RedisTtlWrite;
import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.entity.ForecastRedisEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.mapper.RecordMapper;
import com.softeer.scan.RedisKeyGenerator;
import com.softeer.time.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
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
            List<RedisTtlWrite> bulkData = prepareBulkData(items);
            executePipelinedOperations(bulkData);
            log.info("Successfully updated {} short forecast items to Redis", bulkData.size());
        } catch (Exception e) {
            log.error("Failed to batch update Redis forecast", e);
            throw new RuntimeException("Redis batch update failed", e);
        }
    }

    private List<RedisTtlWrite> prepareBulkData(List<? extends ShortForecastList> items) {
        List<RedisTtlWrite> bulkData = new ArrayList<>();

        items.forEach(item ->
            item.forecasts().forEach(forecast -> {
                try {
                    LocalDate date = forecast.dateTime().toLocalDate();
                    if (date.isAfter(LocalDate.now().plusDays(2))) return;

                    byte[] key = redisKeyGenerator.serializeKey(PREFIX, item.gridId(), ForecastType.SHORT, date);
                    Map<byte[], byte[]> value = recordMapper.toByteMap(new ForecastRedisEntity(forecast, item.gridId()));
                    Duration ttl = TimeUtil.getRedisTtl(forecast.dateTime().plusHours(1));

                    bulkData.add(new RedisTtlWrite(key, value, ttl));

                } catch (Exception e) {
                    log.error("Failed to prepare Redis data for grid ({}, {}) at {} {}", PREFIX, item.gridId(), ForecastType.SHORT, forecast.dateTime());
                }
            })
        );

        return bulkData;
    }

    private void executePipelinedOperations(List<RedisTtlWrite> bulkData) {
        redisTemplate.executePipelined((RedisCallback<?>) connection -> {
            bulkData.forEach(op -> {
                try {
                    connection.hashCommands().hMSet(op.key(), op.value());

                    Duration ttl = op.ttl();
                    if (ttl != null && !ttl.isNegative() && !ttl.isZero()) {
                        connection.keyCommands().pExpire(op.key(), ttl.toMillis());
                    } else {
                        connection.keyCommands().pExpire(op.key(), 1L);
                    }
                } catch (Exception e) {
                    log.warn("Failed to execute pipelined operation for key: {}, skipping this item", new String(op.key()), e);
                }
            });
            return null;
        });
    }
}
