package com.softeer.batch.forecast.mountain.redis;

import com.softeer.batch.common.dto.RedisTtlWrite;
import com.softeer.batch.forecast.chained.context.ExecutionContextKeys;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureKey;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureValue;
import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.entity.ForecastRedisEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.mapper.RecordMapper;
import com.softeer.scan.RedisKeyGenerator;
import com.softeer.time.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
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
            List<RedisTtlWrite> bulkData = prepareBulkData(items);
            executePipelinedOperations(bulkData);
            log.info("Successfully updated {} mountain forecast items to Redis", bulkData.size());
        } catch (Exception e) {
            log.error("Failed to batch update Redis forecast", e);
            throw new RuntimeException("Redis batch update failed", e);
        }
    }

    private List<RedisTtlWrite> prepareBulkData(List<? extends MountainDailyForecast> items) {
        List<RedisTtlWrite> bulkData = new ArrayList<>();

        items.forEach(item ->
            item.hourlyForecasts().forEach(forecast -> {
                try {
                    LocalDate date = forecast.dateTime().toLocalDate();
                    if (date.isAfter(LocalDate.now().plusDays(2))) return;

                    byte[] key = redisKeyGenerator.serializeKey(PREFIX, item.gridId(), ForecastType.MOUNTAIN, forecast.dateTime());
                    DailyTemperatureValue dailyTemperatureValue = dailyTempMap.get(new DailyTemperatureKey(item.gridId(), date));

                    if (dailyTemperatureValue == null) return;

                    ForecastRedisEntity forecastRedisEntity = new ForecastRedisEntity(
                            forecast,
                            dailyTemperatureValue.highestTemperature(),
                            dailyTemperatureValue.lowestTemperature(),
                            item.gridId()
                    );

                    Map<byte[], byte[]> value = recordMapper.toByteMap(forecastRedisEntity);
                    Duration ttl = TimeUtil.getRedisTtl(forecast.dateTime().plusHours(1));

                    bulkData.add(new RedisTtlWrite(key, value, ttl));

                } catch (Exception e) {
                    log.error("Failed to prepare Redis data for grid ({}, {}) at {} {}", PREFIX, item.gridId(), ForecastType.SHORT, forecast.dateTime());
                }
            })
        );

        return bulkData;
    }

    private void executePipelinedOperations(
            List<RedisTtlWrite> bulkData
    ) {
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
