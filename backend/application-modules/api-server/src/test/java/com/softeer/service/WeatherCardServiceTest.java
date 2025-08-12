package com.softeer.service;

import com.softeer.domain.*;
import com.softeer.dto.response.HourlyWeatherResponse;
import com.softeer.dto.response.card.CourseCardResponse;
import com.softeer.dto.response.card.ForecastCardResponse;
import com.softeer.dto.response.card.MountainCardResponse;
import com.softeer.time.HikingTime;
import com.softeer.time.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherCardServiceTest {

    private WeatherCardService target;

    private MountainUseCase mountainUseCase;
    private ForecastUseCase forecastUseCase;

    @BeforeEach
    void setUp() {
        mountainUseCase = mock(MountainUseCase.class);
        forecastUseCase = mock(ForecastUseCase.class);
        target = new WeatherCardService(mountainUseCase, forecastUseCase);
    }

    @Test
    @DisplayName("findForecastsByMountainId: Mountain의 Grid로부터 시작시각 이후 예보를 HourlyWeatherResponse로 매핑한다")
    void findForecastsByMountainId_success() {
        //given
        long mountainId = 7L;
        LocalDateTime startDateTime = LocalDateTime.of(2025, 8, 12, 8, 0);
        LocalDateTime baseTime = TimeUtil.getBaseTime(startDateTime);

        Grid grid = GridFixture.createDefault();
        Mountain mountain = MountainFixture.builder().id(mountainId).grid(grid).build();

        List<Forecast> forecasts = new ArrayList<>();
        for (int hour = 0; hour < 50; hour++) {
            forecasts.add(ForecastFixture.builder().dateTime(baseTime.plusHours(hour)).build());
        }

        when(mountainUseCase.getMountainById(eq(mountainId))).thenReturn(mountain);
        when(forecastUseCase.findForecastsFromStartDateTime(eq(grid), eq(startDateTime))).thenReturn(forecasts);

        List<HourlyWeatherResponse> expected = forecasts.stream().map(HourlyWeatherResponse::new).toList();

        //when
        List<HourlyWeatherResponse> result = target.findForecastsByMountainId(mountainId, startDateTime);

        //then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("createMountainCard: baseTime 기준 Grid 날씨조건으로 MountainCardResponse 생성")
    void createMountainCard_success() {
        //given
        long mountainId = 7L;
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 12, 8, 0);
        LocalDateTime baseTime = TimeUtil.getBaseTime(dateTime);

        Grid grid = GridFixture.createDefault();
        Mountain mountain = MountainFixture.builder().id(mountainId).grid(grid).build();

        Forecast surefaceForecast = ForecastFixture.builder().dateTime(baseTime).temperature(10).build();
        Forecast topForecast = ForecastFixture.builder().dateTime(baseTime).temperature(20).build();

        ForecastUseCase.WeatherCondition weatherCondition = new ForecastUseCase.WeatherCondition(surefaceForecast, topForecast);

        when(mountainUseCase.getMountainById(mountainId)).thenReturn(mountain);
        when(forecastUseCase.findForecastWeatherCondition(eq(grid), any(LocalDateTime.class))).thenReturn(weatherCondition);

        MountainCardResponse expected = new MountainCardResponse(mountain, weatherCondition);

        //when
        MountainCardResponse response = target.createMountainCard(mountainId);

        //then
        assertEquals(expected, response);
    }

    @Test
    @DisplayName("createCourseCard: 주어진 courseId와 dateTime으로 CoursePlan 조회 후 Grid 날씨조건으로 카드 생성")
    void createCourseCard_success() {
        //given
        long mountainId = 100L;
        long courseId = 42L;

        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 12, 9, 30);
        LocalDateTime baseTime = TimeUtil.getBaseTime(dateTime);

        Grid startGrid = GridFixture.builder().id(1).build();
        Grid destinationGrid = GridFixture.builder().id(2).build();
        Mountain mountain = MountainFixture.builder().id(mountainId).grid(destinationGrid).build();
        Course course = CourseFixture.builder().id(courseId).startGrid(startGrid).destinationGrid(destinationGrid).build();

        CoursePlan coursePlan = CoursePlanFixture.builder().mountain(mountain).course(course).build();

        Forecast surefaceForecast = ForecastFixture.builder().dateTime(baseTime).temperature(10).build();
        Forecast topForecast = ForecastFixture.builder().dateTime(baseTime).temperature(20).build();

        ForecastUseCase.WeatherCondition weatherCondition = new ForecastUseCase.WeatherCondition(surefaceForecast, topForecast);

        when(mountainUseCase.getCoursePlan(eq(courseId), any(LocalDate.class))).thenReturn(coursePlan);
        when(forecastUseCase.findForecastWeatherCondition(eq(destinationGrid), eq(baseTime))).thenReturn(weatherCondition);

        CourseCardResponse expected = new CourseCardResponse(course, weatherCondition);

        //when
        CourseCardResponse result = target.createCourseCard(courseId, dateTime);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("createForecastCard: 등산 시간 구간별 예보 조회로 ForecastCardResponse 생성")
    void createForecastCard_success() {
        //given
        long mountainId = 100L;
        long courseId = 42L;
        int mountainAltitude = 142;
        int courseAltitude = 70;

        LocalDateTime startDateTime = LocalDateTime.of(2025, 8, 12, 9, 30);
        LocalDateTime baseTime = TimeUtil.getBaseTime(startDateTime);

        Grid startGrid = GridFixture.builder().id(1).build();
        Grid destinationGrid = GridFixture.builder().id(2).build();
        Mountain mountain = MountainFixture.builder().id(mountainId).altitude(mountainAltitude).grid(destinationGrid).build();
        Course course = CourseFixture.builder().id(courseId).altitude(courseAltitude).startGrid(startGrid).destinationGrid(destinationGrid).build();

        CoursePlan coursePlan = CoursePlanFixture.builder().mountain(mountain).course(course).build();

        Forecast startForecast = ForecastFixture.builder().dateTime(baseTime).temperature(10).build();
        Forecast arrivalForecast = ForecastFixture.builder().dateTime(baseTime).temperature(20).build();
        Forecast descentForecast = ForecastFixture.builder().dateTime(baseTime).temperature(15).build();

        ForecastUseCase.CourseForecast courseForecast = new ForecastUseCase.CourseForecast(startForecast, arrivalForecast, descentForecast);

        when(mountainUseCase.getCoursePlan(eq(courseId), any(LocalDate.class))).thenReturn(coursePlan);
        when(forecastUseCase.findForecastsByHikingTime(eq(course.startGrid()), eq(course.destinationGrid()), any(HikingTime.class) // HikingTime
        )).thenReturn(courseForecast);

        ForecastCardResponse expected = ForecastCardResponse.from(courseForecast, courseAltitude, mountainAltitude);

        //when
        ForecastCardResponse response = target.createForecastCard(courseId, startDateTime);

        //then
        assertEquals(expected, response);
    }
}
