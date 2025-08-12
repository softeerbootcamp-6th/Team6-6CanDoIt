package com.softeer.service;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.domain.GridFixture;
import com.softeer.domain.ForecastFixture;
import com.softeer.entity.enums.ForecastType;
import com.softeer.error.CustomException;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.ForecastException;
import com.softeer.repository.ForecastAdapter;
import com.softeer.service.impl.ForecastUseCaseImpl;
import com.softeer.time.HikingTime;
import com.softeer.time.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ForecastUseCaseTest {


    private ForecastUseCase target;
    private ForecastAdapter forecastAdapter;

    @BeforeEach
    void setUp() {
        forecastAdapter = mock(ForecastAdapter.class);
        target = new ForecastUseCaseImpl(forecastAdapter);
    }


    @Test
    @DisplayName("현재 기준 base time으로 어댑터를 호출하고 그대로 반환한다")
    void findForecastsFromStartDateTime_success() {
        // given
        Grid grid = GridFixture.createDefault();

        LocalDateTime startDateTime = TimeUtil.getBaseTime(LocalDateTime.of(2025, 8, 1, 10, 20));
        LocalDateTime endDateTime = startDateTime.plusDays(3);

        int hour = 50;
        List<Forecast> expected = new ArrayList<>(hour);

        for(int i = 0; i < 50; i++) {
            expected.add(ForecastFixture.builder().id(i + 1).dateTime(startDateTime.plusHours(i)).build());
        }

        when(forecastAdapter.findForecastsBetweenDateTime(grid.id(), startDateTime, endDateTime)).thenReturn(expected);

        // when
        List<Forecast> result = target.findForecastsFromStartDateTime(grid, startDateTime);

        // then
        assertThat(result).isEqualTo(expected);

        ArgumentCaptor<LocalDateTime> timeCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(forecastAdapter, times(1)).findForecastsBetweenDateTime(eq(grid.id()), timeCaptor.capture(), eq(endDateTime));

        LocalDateTime captured = timeCaptor.getValue();
        assertThat(startDateTime).isEqualTo(captured);
    }

    @Test
    @DisplayName("출발/정상/하산 시간별 예보를 조회해 CourseForecast를 반환한다")
    void findForecastsByHikingTime_success() {
        // given
        Grid startGrid = GridFixture.builder().id(101).build();
        Grid destinationGrid = GridFixture.builder().id(102).build();

        LocalDateTime start = LocalDateTime.of(2025, 8, 10, 9, 37, 21);
        LocalDateTime arrival = start.plusHours(3).plusMinutes(50);  // 등정
        LocalDateTime descent = start.plusHours(6).plusMinutes(40);  // 하산

        HikingTime hikingTime = new HikingTime(start, arrival, descent);

        Forecast startForecast = ForecastFixture.builder().id(1L).forecastType(ForecastType.SHORT).dateTime(hikingTime.startTime()).build();
        Forecast arrivalForecast = ForecastFixture.builder().id(2L).forecastType(ForecastType.MOUNTAIN).dateTime(hikingTime.arrivalTime()).build();
        Forecast descentForecast = ForecastFixture.builder().id(3L).forecastType(ForecastType.SHORT).dateTime(hikingTime.descentTime()).build();

        when(forecastAdapter.findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime())).thenReturn(Optional.of(startForecast));
        when(forecastAdapter.findForecastByTypeAndDateTime(destinationGrid.id(), ForecastType.MOUNTAIN, hikingTime.arrivalTime())).thenReturn(Optional.of(arrivalForecast));
        when(forecastAdapter.findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.descentTime())).thenReturn(Optional.of(descentForecast));


        ForecastUseCase.CourseForecast expected = new ForecastUseCase.CourseForecast(startForecast, arrivalForecast, descentForecast);

        // when
        ForecastUseCase.CourseForecast result = target.findForecastsByHikingTime(startGrid, destinationGrid, hikingTime);

        // then
        assertThat(result).isEqualTo(expected);
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime());
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(destinationGrid.id(), ForecastType.MOUNTAIN, hikingTime.arrivalTime());
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.descentTime());
    }

    @Test
    @DisplayName("시작 지점 예보가 없으면 CustomException(FCT-001)을 던진다")
    void findForecastsByHikingTime_notFoundStartForecast_throws() {
        // given
        Grid startGrid = GridFixture.builder().id(101).build();
        Grid destinationGrid = GridFixture.builder().id(102).build();

        LocalDateTime start = LocalDateTime.of(2025, 8, 10, 9, 37, 21);
        LocalDateTime arrival = start.plusHours(3).plusMinutes(50);  // 등정
        LocalDateTime descent = start.plusHours(6).plusMinutes(40);  // 하산

        HikingTime hikingTime = new HikingTime(start, arrival, descent);

        CustomException expected = ExceptionCreator.create(ForecastException.NOT_FOUND);

        when(forecastAdapter.findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime())).thenReturn(Optional.empty());

        // when
        CustomException ex = assertThrows(CustomException.class, () -> target.findForecastsByHikingTime(startGrid, destinationGrid, hikingTime));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ForecastException.NOT_FOUND.getErrorCode());
        assertThat(ex.getMessage()).isEqualTo(expected.getMessage());
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime());
    }

    @Test
    @DisplayName("도착 지점 예보가 없으면 CustomException(FCT-001)을 던진다")
    void findForecastsByHikingTime_notFoundArrivalForecast_throws() {
        // given
        Grid startGrid = GridFixture.builder().id(101).build();
        Grid destinationGrid = GridFixture.builder().id(102).build();

        LocalDateTime start = LocalDateTime.of(2025, 8, 10, 7, 12, 0);
        LocalDateTime arrival = start.plusHours(2);
        LocalDateTime descent = start.plusHours(4);
        HikingTime hikingTime = new HikingTime(start, arrival, descent);

        CustomException expected = ExceptionCreator.create(ForecastException.NOT_FOUND);

        Forecast startForecast = ForecastFixture.builder().id(1L).forecastType(ForecastType.SHORT).dateTime(hikingTime.startTime()).build();

        when(forecastAdapter.findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime())).thenReturn(Optional.of(startForecast));
        when(forecastAdapter.findForecastByTypeAndDateTime(destinationGrid.id(), ForecastType.MOUNTAIN, hikingTime.arrivalTime())).thenReturn(Optional.empty());

        // when
        CustomException ex = assertThrows(CustomException.class, () -> target.findForecastsByHikingTime(startGrid, destinationGrid, hikingTime));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ForecastException.NOT_FOUND.getErrorCode());
        assertThat(ex.getMessage()).isEqualTo(expected.getMessage());
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime());
    }

    @Test
    @DisplayName("마지막 지점 예보가 없으면 CustomException(FCT-001)을 던진다")
    void findForecastsByHikingTime_notFound_throws() {
        // given
        Grid startGrid = GridFixture.builder().id(101).build();
        Grid destinationGrid = GridFixture.builder().id(102).build();

        LocalDateTime start = LocalDateTime.of(2025, 8, 10, 7, 12, 0);
        LocalDateTime arrival = start.plusHours(2);
        LocalDateTime descent = start.plusHours(4);
        HikingTime hikingTime = new HikingTime(start, arrival, descent);


        Forecast startForecast = ForecastFixture.builder().id(1L).forecastType(ForecastType.SHORT).dateTime(hikingTime.startTime()).build();
        Forecast arrivalForecast = ForecastFixture.builder().id(2L).forecastType(ForecastType.MOUNTAIN).dateTime(hikingTime.arrivalTime()).build();


        when(forecastAdapter.findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime())).thenReturn(Optional.of(startForecast));
        when(forecastAdapter.findForecastByTypeAndDateTime(destinationGrid.id(), ForecastType.MOUNTAIN, hikingTime.arrivalTime())).thenReturn(Optional.of(arrivalForecast));
        when(forecastAdapter.findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.descentTime())).thenReturn(Optional.empty());

        CustomException expected = ExceptionCreator.create(ForecastException.NOT_FOUND);

        // when
        CustomException ex = assertThrows(CustomException.class, () -> target.findForecastsByHikingTime(startGrid, destinationGrid, hikingTime));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ForecastException.NOT_FOUND.getErrorCode());
        assertThat(ex.getMessage()).isEqualTo(expected.getMessage());
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(startGrid.id(), ForecastType.SHORT, hikingTime.startTime());
        verify(forecastAdapter, times(1)).findForecastByTypeAndDateTime(destinationGrid.id(), ForecastType.MOUNTAIN, hikingTime.arrivalTime());
    }

    @Test
    void findForecastWeatherCondition() {
        //given
        Grid grid = GridFixture.builder().id(101).build();
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 10, 7, 12, 0);

        Forecast shortForecast = ForecastFixture.builder().forecastType(ForecastType.SHORT).dateTime(dateTime).build();
        Forecast topForecast = ForecastFixture.builder().forecastType(ForecastType.MOUNTAIN).dateTime(dateTime).build();

        when(forecastAdapter.findForecastByTypeAndDateTime(grid.id(), ForecastType.SHORT, dateTime)).thenReturn(Optional.of(shortForecast));
        when(forecastAdapter.findForecastByTypeAndDateTime(grid.id(), ForecastType.MOUNTAIN, dateTime)).thenReturn(Optional.of(topForecast));

        ForecastUseCase.WeatherCondition weatherCondition = new ForecastUseCase.WeatherCondition(shortForecast, topForecast);

        //when
        ForecastUseCase.WeatherCondition result = target.findForecastWeatherCondition(grid, dateTime);

        //then
        assertThat(result).isEqualTo(weatherCondition);
    }
}
