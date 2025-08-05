package com.softeer.repository.impl;

import com.softeer.domain.Forecast;
import com.softeer.domain.ForecastFixture;
import com.softeer.domain.Grid;
import com.softeer.domain.GridFixture;
import com.softeer.entity.ForecastEntity;
import com.softeer.entity.GridEntity;
import com.softeer.entity.ShortForecastDetailEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.repository.GridJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class ForecastJdbcRepositoryTest {

    @Autowired
    private ForecastJdbcRepository target;

    @Autowired
    private GridJpaRepository gridJpaRepository;

    @Autowired
    private ForecastJpaRepository forecastJpaRepository;

    @Autowired
    private ShortForecastDetailJpaRepository shortForecastDetailJpaRepository;

    private GridEntity expectedGridEntity;
    private GridEntity mockGridEntity;

    @BeforeEach
    void setUp() {
        expectedGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(55).y(126).build()));
        mockGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(56).y(128).build()));
    }

    @ParameterizedTest(name = "{index} ⇒ 초단기 예보 {0}개 조회")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})      // 최대 6개까지 시나리오
    void findForecasts_VaryingUltraCount(int ultraCount) {

        //Given
        LocalDateTime baseTime = LocalDateTime.of(2023, 11, 1, 0, 0);
        Grid grid = GridEntity.toDomainEntity(expectedGridEntity);

        List<Forecast> expected = new ArrayList<>(24);

        for (int i = 0; i < 24; i++) {
            LocalDateTime dateTime   = baseTime.plusHours(i);
            double precipitationProbability = i * 2;

            if (i < ultraCount) {
                Forecast ultraForecast = ForecastFixture.builder().dateTime(dateTime).forecastType(ForecastType.ULTRA).build();
                Forecast shortForecast = ForecastFixture.builder()
                        .dateTime(dateTime)
                        .forecastType(ForecastType.SHORT)
                        .precipitationProbability(precipitationProbability)
                        .build();

                long id = persistUltraForecast(ForecastEntity.from(ultraForecast, grid));
                getShortForecastId(shortForecast, grid);

                expected.add(ForecastFixture.builder().id(id)
                        .dateTime(dateTime).forecastType(ForecastType.ULTRA)
                        .precipitationProbability(precipitationProbability)
                        .build());
            } else {
                Forecast shortForecast = ForecastFixture.builder().dateTime(dateTime).forecastType(ForecastType.SHORT).precipitationProbability(precipitationProbability).build();

                long id = getShortForecastId(shortForecast, grid);

                expected.add(ForecastFixture.builder().id(id)
                        .dateTime(dateTime).forecastType(ForecastType.SHORT)
                        .precipitationProbability(precipitationProbability)
                        .build());
            }
        }

        //When
        List<Forecast> result = target.findForecastsFor24Hours(grid.id(), baseTime);

        //Then
        Assertions.assertEquals(24, result.size(), "총 개수 24개 확인");
        Assertions.assertEquals(expected, result, "데이터 내용 동일");
    }



    @Test
    @DisplayName("입력값으로 준 grid의 예보만 조회하는 테스트")
    void saveAndFindMixedForecasts_shouldWorkCorrectly() {
        // Given
        LocalDateTime baseTime = LocalDateTime.of(2023, 11, 1, 0, 0);

        Grid expectGrid = GridEntity.toDomainEntity(expectedGridEntity);
        Grid mockGrid = GridEntity.toDomainEntity(mockGridEntity);

        List<Forecast> expected = new ArrayList<>(24);

        for (int i = 0; i < 6; i++) {
            LocalDateTime dateTime = baseTime.plusHours(i);
            int temperature = i * 2;
            double precipitationProbability = i * 2;

            Forecast ultraForecast = ForecastFixture.builder()
                    .dateTime(dateTime)
                    .temperature(temperature)
                    .forecastType(ForecastType.ULTRA)
                    .build();

            Forecast shortForecast = ForecastFixture.builder()
                    .dateTime(dateTime)
                    .forecastType(ForecastType.SHORT)
                    .precipitationProbability(precipitationProbability)
                    .build();

            Long id = persistUltraForecast(ForecastEntity.from(ultraForecast, expectGrid));

            getShortForecastId(shortForecast, expectGrid);

            expected.add(ForecastFixture.builder().id(id).forecastType(ForecastType.ULTRA)
                    .dateTime(dateTime)
                    .temperature(temperature)
                    .precipitationProbability(precipitationProbability)
                    .build());
        }

        for (int i = 0; i < 6; i++) {
            Forecast ultraForecast = ForecastFixture.builder()
                    .dateTime(baseTime.plusHours(i))
                    .forecastType(ForecastType.ULTRA)
                    .build();

            persistUltraForecast(ForecastEntity.from(ultraForecast, mockGrid));
        }

        for (int i = 6; i < 24; i++) {
            LocalDateTime dateTime = baseTime.plusHours(i);
            int temperature = i * 3;
            double precipitationProbability = i * 3;

            Forecast shortForecast = ForecastFixture.builder()
                    .dateTime(baseTime.plusHours(i))
                    .forecastType(ForecastType.SHORT)
                    .temperature(temperature)
                    .precipitationProbability(precipitationProbability)
                    .build();

            Long id = getShortForecastId(shortForecast, expectGrid);

            expected.add(ForecastFixture.builder().id(id).forecastType(ForecastType.SHORT)
                    .dateTime(dateTime)
                    .temperature(temperature)
                    .precipitationProbability(precipitationProbability)
                    .build());
        }

        for (int i = 0; i < 24; i++) {
            Forecast shortForecast = ForecastFixture.builder()
                    .dateTime(baseTime.plusHours(i))
                    .forecastType(ForecastType.SHORT)
                    .build();

            getShortForecastId(shortForecast, mockGrid);
        }

        //When
        List<Forecast> result = target.findForecastsFor24Hours(expectGrid.id(), baseTime);

        //Then
        Assertions.assertEquals(24, result.size(), "총 개수 24개 확인");
        Assertions.assertEquals(expected, result, "데이터 내용 동일");
    }

    private Long getShortForecastId(Forecast shortForecast, Grid expectGrid) {
        return persistShortForecast(ForecastEntity.from(shortForecast, expectGrid),
                shortForecast.precipitationCondition().precipitationProbability(),
                shortForecast.precipitationCondition().snowAccumulation(),
                shortForecast.dailyTemperature().highestTemperature(),
                shortForecast.dailyTemperature().lowestTemperature()
        );
    }


    private Long persistUltraForecast(ForecastEntity forecastEntity) {
        return forecastJpaRepository.saveAndFlush(forecastEntity).getId();
    }

    private Long persistShortForecast(ForecastEntity forecastEntity, double precipitationProbability, double snowAccumulation,
                                      int highestTemperature, int lowestTemperature
                                      ) {
        ForecastEntity forecast = forecastJpaRepository.saveAndFlush(forecastEntity);

        shortForecastDetailJpaRepository.saveAndFlush(
                new ShortForecastDetailEntity(
                        forecast,
                        precipitationProbability,
                        snowAccumulation,
                        highestTemperature,
                        lowestTemperature
                ));

        return forecast.getId();
    }
}