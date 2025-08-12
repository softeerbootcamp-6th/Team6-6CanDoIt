package com.softeer.dto.response.card;

import com.softeer.domain.Course;
import com.softeer.service.ForecastUseCase;

public record CourseCardResponse(String courseImageUrl, double totalDuration, double totalDistance, WeatherMetric weatherMetric, String hikingActivityStatus) {
    public CourseCardResponse(Course course, ForecastUseCase.WeatherCondition weatherCondition) {
        this(course.imageUrl(), course.totalDuration(), course.totalDistance(), new WeatherMetric(weatherCondition), weatherCondition.hikingActivityStatus());
    }
}
