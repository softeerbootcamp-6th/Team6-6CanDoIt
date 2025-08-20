package com.softeer.dto.response.card;

import com.softeer.domain.Course;
import com.softeer.domain.SunTime;
import com.softeer.service.ForecastUseCase;

import java.time.LocalTime;

public record CourseCardResponse(
        String courseImageUrl,
        double totalDuration,
        double totalDistance,
        LocalTime sunrise,
        LocalTime sunset,
        String hikingActivityStatus
) {
    public CourseCardResponse(
            Course course,
            SunTime sunTime,
            ForecastUseCase.WeatherCondition weatherCondition
    ) {
        this(
                course.imageUrl(),
                course.totalDuration(),
                course.totalDistance(),
                sunTime.sunrise(),
                sunTime.sunset(),
                weatherCondition.hikingActivityStatus()
        );
    }
}
