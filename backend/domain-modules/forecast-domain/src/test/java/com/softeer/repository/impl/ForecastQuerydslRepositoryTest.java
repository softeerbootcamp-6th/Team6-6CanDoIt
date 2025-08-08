package com.softeer.repository.impl;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.domain.*;
import com.softeer.entity.DailyTemperatureEntity;
import com.softeer.entity.ForecastEntity;
import com.softeer.entity.GridEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.repository.GridJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTestWithContainer
@Transactional
class ForecastQuerydslRepositoryTest {

    private static final int HOURS_PER_DAY = 24;

    @Autowired
    private ForecastQuerydslRepository target;

    @Autowired
    private GridJpaRepository gridJpaRepository;

    @Autowired
    private ForecastJpaRepository forecastJpaRepository;

    @Autowired
    private DailyTemperatureJpaRepository dailyTemperatureJpaRepository;

    @Test
    @DisplayName("시작 시간 이후부터의 해당 grid의 예보만 조회하는 테스트")
    void findForecastsBetweenDateTimeTest() {
        // Given
        GridEntity expectedGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(55).y(126).build()));
        GridEntity mockGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(56).y(128).build()));

        int hour = HOURS_PER_DAY * 2;
        LocalDateTime startTime = LocalDateTime.of(2023, 11, 1, 0, 0);
        LocalDateTime endTime = startTime.plusHours(hour);

        Grid expectGrid = GridEntity.toDomainEntity(expectedGridEntity);
        Grid mockGrid = GridEntity.toDomainEntity(mockGridEntity);

        List<Forecast> expected = new ArrayList<>(hour);
        DailyTemperature expectedDailyTemperature = DailyTemperatureFixture.createDefault();
        DailyTemperature mockDailyTemperature = DailyTemperatureFixture.builder().highestTemperature(Double.MAX_VALUE).build();

        int dayRange = endTime.getDayOfYear() - startTime.getDayOfYear();

        for (int i = 0; i <= dayRange; i++) {
            LocalDate date = LocalDate.from(startTime.plusDays(i));
            persistDailyTemperature(expectedDailyTemperature, date, expectGrid);
        }


        for (int i = 0; i < hour; i++) {
            LocalDateTime dateTime = startTime.plusHours(i);
            int temperature = i * 2;

            Forecast shortForecast = ForecastFixture.builder()
                    .dateTime(dateTime)
                    .temperature(temperature)
                    .forecastType(ForecastType.SHORT)
                    .dailyTemperature(expectedDailyTemperature)
                    .build();

            Forecast mountainForecast = ForecastFixture.builder()
                    .dateTime(dateTime)
                    .forecastType(ForecastType.MOUNTAIN)
                    .dailyTemperature(mockDailyTemperature)
                    .build();

            Long id = persistForecast(shortForecast, expectGrid);
            persistForecast(mountainForecast, mockGrid);

            expected.add(ForecastFixture.builder().id(id).forecastType(ForecastType.SHORT)
                    .dateTime(dateTime)
                    .temperature(temperature)
                    .build());
        }

        //When
        List<Forecast> result = target.findForecastsAfterStartDateTime(expectGrid.id(), startTime);

        //Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("입력값을 제대로 줬을 때 해당 예보 봔환")
    void findForecastByTypeAndDateTime_With_Valid_Input() {
        // Given
        GridEntity expectedGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(55).y(126).build()));
        GridEntity mockGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(56).y(128).build()));

        ForecastType forecastType = ForecastType.SHORT;
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 1, 0, 0);
        LocalDate date = LocalDate.from(dateTime);

        Grid expectGrid = GridEntity.toDomainEntity(expectedGridEntity);
        Grid mockGrid = GridEntity.toDomainEntity(mockGridEntity);

        DailyTemperature dailyTemperature = DailyTemperatureFixture.createDefault();
        persistDailyTemperature(dailyTemperature, date, expectGrid);

        Forecast expected_With_Valid_Input = ForecastFixture.builder().forecastType(forecastType).dateTime(dateTime).build();
        Forecast mock_With_Wrong_DateTime = ForecastFixture.builder().forecastType(forecastType).dateTime(dateTime.plusDays(1)).build();
        Forecast mock_With_Wrong_Grid = ForecastFixture.builder().forecastType(forecastType).dateTime(dateTime).build();
        Forecast mock_With_Wrong_Type = ForecastFixture.builder().forecastType(ForecastType.MOUNTAIN).build();

        Long id = persistForecast(expected_With_Valid_Input, expectGrid);
        persistForecast(mock_With_Wrong_DateTime, expectGrid);
        persistForecast(mock_With_Wrong_Grid, mockGrid);
        persistForecast(mock_With_Wrong_Type, expectGrid);

        Forecast expected = ForecastFixture.builder().id(id).forecastType(forecastType).dateTime(dateTime).build();

        //When
        Optional<Forecast> result = target.findForecastByTypeAndDateTime(expectGrid.id(), forecastType, dateTime);

        //Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected, result.get());
    }

    @Test
    @DisplayName("입력값을 잘못줘서 Optional.empty() 반환")
    void findForecastByTypeAndDateTime_With_Valid_Inpu() {
        // Given
        GridEntity expectedGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(55).y(126).build()));
        GridEntity mockGridEntity = gridJpaRepository.save(GridEntity.from(GridFixture.builder().x(56).y(128).build()));

        ForecastType forecastType = ForecastType.SHORT;
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 1, 0, 0);
        LocalDate date = LocalDate.from(dateTime);

        Grid expectGrid = GridEntity.toDomainEntity(expectedGridEntity);
        Grid mockGrid = GridEntity.toDomainEntity(mockGridEntity);

        DailyTemperature dailyTemperature = DailyTemperatureFixture.createDefault();
        persistDailyTemperature(dailyTemperature, date, expectGrid);

        Forecast mock_With_Wrong_DateTime = ForecastFixture.builder().forecastType(forecastType).dateTime(dateTime.plusDays(1)).build();
        Forecast mock_With_Wrong_Grid = ForecastFixture.builder().forecastType(forecastType).dateTime(dateTime).build();
        Forecast mock_With_Wrong_Type = ForecastFixture.builder().forecastType(ForecastType.MOUNTAIN).build();

        persistForecast(mock_With_Wrong_DateTime, expectGrid);
        persistForecast(mock_With_Wrong_Grid, mockGrid);
        persistForecast(mock_With_Wrong_Type, expectGrid);

        //When
        Optional<Forecast> result = target.findForecastByTypeAndDateTime(expectGrid.id(), forecastType, dateTime);

        //Then
        Assertions.assertTrue(result.isEmpty());
    }

    private void persistDailyTemperature(DailyTemperature dailyTemperature, LocalDate localDate, Grid grid) {
        DailyTemperatureEntity dailyTemperatureEntity = new DailyTemperatureEntity(DailyTemperatureFixture.DEFAULT_ID, localDate,
                dailyTemperature.highestTemperature(),
                dailyTemperature.lowestTemperature(),
                GridEntity.from(grid)
        );

        dailyTemperatureJpaRepository.saveAndFlush(dailyTemperatureEntity);
    }

    private Long persistForecast(Forecast forecast, Grid grid) {
        return forecastJpaRepository.saveAndFlush(ForecastEntity.from(forecast, grid)).getId();
    }
}