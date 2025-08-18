package com.softeer.service.impl;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.domain.Forecast;
import com.softeer.domain.ForecastRedisEntityFixture;
import com.softeer.entity.ForecastRedisEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.mapper.RecordMapper;
import com.softeer.scan.RedisKeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTestWithContainer
class ForecastRedisQueryServiceTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ForecastRedisQueryService forecastRedisQueryService;
    @Autowired
    private RedisKeyGenerator redisKeyGenerator;
    @Autowired
    private RecordMapper  recordMapper;


    private static final int GRID_ID = 9999;
    private static final ForecastType TYPE = ForecastType.SHORT;
    private static final int testGridId = 101;
    private static final LocalDateTime dateTime = LocalDateTime.of(2025, 8, 18, 0, 0);
    private ForecastRedisEntity  forecastRedisEntity_1;
    private ForecastRedisEntity  forecastRedisEntity_2;


    private ForecastRedisEntity insertForecastIntoRedis(int gridId, ForecastType forecastType) {
        ForecastRedisEntity entity = ForecastRedisEntityFixture.builder().gridId(gridId).dateTime(dateTime).build();
        String key = redisKeyGenerator.generateKey("forecast", gridId, forecastType, entity.dateTime());
        redisTemplate.opsForHash().putAll(key, recordMapper.toMap(entity));
        return entity;
    }

    @BeforeEach
    void setUp() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        forecastRedisEntity_1 = insertForecastIntoRedis(testGridId, ForecastType.SHORT);
        forecastRedisEntity_2 = insertForecastIntoRedis(102, ForecastType.SHORT);
        insertForecastIntoRedis(testGridId, ForecastType.MOUNTAIN);
    }

    @Test
    @DisplayName("findShortForecasts - 단건 리스트 조회")
    void testFindShortForecasts() {
        List<Forecast> expected = Stream.of(forecastRedisEntity_1)
                .map(ForecastRedisEntity::toDomain)
                .toList();

        List<Forecast> forecasts = forecastRedisQueryService.findShortForecasts(forecastRedisEntity_1.gridId());

        assertThat(forecasts).isEqualTo(expected);
    }

    @Test
    @DisplayName("findForecastByGridIdAndTypeAndDateTime - 단건 조회")
    void testFindForecastByGridIdAndTypeAndDateTime() {

        Forecast expected = ForecastRedisEntity.toDomain(forecastRedisEntity_1);

        Forecast forecast = forecastRedisQueryService.findForecastByGridIdAndTypeAndDateTime(forecastRedisEntity_1.gridId(), ForecastType.SHORT, dateTime);

        assertThat(forecast).isEqualTo(expected);
    }

    @Test
    @DisplayName("findForecastsByGridIdAndTypeAndDateTime - 멀티 조회")
    void testFindForecastsByGridIdAndTypeAndDateTime() {

        List<Integer> gridIds = List.of(forecastRedisEntity_1.gridId(), forecastRedisEntity_2.gridId());

        Map<Integer, Forecast> expected = Stream.of(forecastRedisEntity_1, forecastRedisEntity_2)
                .collect(Collectors.toMap(ForecastRedisEntity::gridId, ForecastRedisEntity::toDomain));

        Map<Integer, Forecast> result = forecastRedisQueryService.findForecastsByGridIdAndTypeAndDateTime(gridIds, ForecastType.SHORT, dateTime);

        assertThat(result).isEqualTo(expected);
    }


    @Test
    @DisplayName("findForecastByGridIdAndTypeAndDateTime - Redis에 저장된 단일 Forecast 조회")
    void findForecastByGridIdAndTypeAndDateTime_shouldReturnForecast() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 17, 12, 0);
        ForecastRedisEntity entity = ForecastRedisEntityFixture.builder()
                .id(123L)
                .dateTime(dateTime)
                .forecastType(TYPE)
                .sky(com.softeer.entity.enums.Sky.CLOUDY)
                .temperature(21.5)
                .humidity(60.0)
                .windDir(com.softeer.entity.enums.WindDirection.E)
                .windSpeed(2.2)
                .precipitationType(com.softeer.entity.enums.PrecipitationType.RAIN)
                .precipitation("1mm")
                .precipitationProbability(30.0)
                .snowAccumulation("0cm")
                .highestTemperature(24.0)
                .lowestTemperature(18.0)
                .build();

        String key = redisKeyGenerator.generateKey("forecast", GRID_ID, TYPE, dateTime);

        Map<String, Object> map = recordMapper.toMap(entity);

        // put to redis
        redisTemplate.opsForHash().putAll(key, map);

        Forecast expected = ForecastRedisEntity.toDomain(entity);

        // when
        Forecast actual = forecastRedisQueryService.findForecastByGridIdAndTypeAndDateTime(GRID_ID, TYPE, dateTime);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("findShortForecasts - 여러 키를 스캔하여 Forecast 리스트 반환")
    void findShortForecasts_shouldReturnList() {
        // given: 두 개의 forecast 엔트리 삽입
        LocalDateTime t1 = LocalDateTime.of(2025, 8, 17, 9, 0);
        LocalDateTime t2 = LocalDateTime.of(2025, 8, 17, 12, 0);

        ForecastRedisEntity e1 = ForecastRedisEntityFixture.builder()
                .id(1L).dateTime(t1).forecastType(TYPE).temperature(20.0).build();
        ForecastRedisEntity e2 = ForecastRedisEntityFixture.builder()
                .id(2L).dateTime(t2).forecastType(TYPE).temperature(23.0).build();

        String key1 = redisKeyGenerator.generateKey("forecast", GRID_ID, TYPE, t1);
        String key2 = redisKeyGenerator.generateKey("forecast", GRID_ID, TYPE, t2);

        Map<String, Object> m1 = recordMapper.toMap(e1);
        Map<String, Object> m2 = recordMapper.toMap(e2);

        redisTemplate.opsForHash().putAll(key1, m1);
        redisTemplate.opsForHash().putAll(key2, m2);

        List<Forecast> expected = Stream.of(e1, e2)
                .map(ForecastRedisEntity::toDomain)
                .toList();

        // when
        List<Forecast> list = forecastRedisQueryService.findShortForecasts(GRID_ID);

        // then
        assertThat(list).isEqualTo(expected);
    }
}
