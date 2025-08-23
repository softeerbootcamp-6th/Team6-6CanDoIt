package com.softeer.presentation.impl;

import com.softeer.dto.response.CourseInfoResponse;
import com.softeer.dto.response.HourlyWeatherResponse;
import com.softeer.dto.response.card.*;
import com.softeer.presentation.WeatherCardApi;
import com.softeer.service.WeatherCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherCardController implements WeatherCardApi {

    private final WeatherCardService weatherCardService;

    @Override
    public ResponseEntity<List<MountainCardResponse>> mountainCards() {
        return ResponseEntity.ok(weatherCardService.createMountainCards());
    }

    @Override
    public ResponseEntity<List<CourseInfoResponse>> courseInfos(long mountainId) {
        return ResponseEntity.ok(weatherCardService.findCoursesByMountainId(mountainId));
    }

    @Override
    public ResponseEntity<CourseCardResponse> courseCard(Long courseId, LocalDateTime dateTime) {
        return ResponseEntity.ok(weatherCardService.createCourseCard(courseId, dateTime));
    }

    @Override
    public ResponseEntity<ForecastCardResponse> forecastCard(Long courseId, LocalDateTime startDateTime) {
        return ResponseEntity.ok(weatherCardService.createForecastCard(courseId, startDateTime));
    }

    @Override
    public ResponseEntity<List<HourlyWeatherResponse>> hourlyWeatherForecasts(Long mountainId, LocalDateTime startDateTime) {
        return ResponseEntity.ok(weatherCardService.findForecastsByMountainId(mountainId, startDateTime));
    }

    @Override
    public ResponseEntity<MountainCourseCardResponse> mountainCourse(Long courseId, LocalDateTime dateTime) {
        return ResponseEntity.ok(weatherCardService.createMountainCourseCard(courseId, dateTime));
    }

    @Override
    public ResponseEntity<CourseScheduleCardResponse> courseSchedule(Long courseId, LocalDateTime startDateTime) {
        return ResponseEntity.ok(weatherCardService.createCourseScheduleCard(courseId, startDateTime));
    }
}
