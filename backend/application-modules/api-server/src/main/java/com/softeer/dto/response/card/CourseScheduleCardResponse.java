package com.softeer.dto.response.card;

import com.softeer.domain.CoursePlan;
import com.softeer.domain.Forecast;
import com.softeer.time.HikingTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record CourseScheduleCardResponse(
        LocalDate date,
        LocalTime startTime,
        LocalTime descentTime,
        String mountainName,
        String courseName,
        double distance,
        ForecastOverview startForecast,
        ForecastOverview arrivalForecast,
        ForecastOverview descentForecast,
        double lowestTemperature,
        double highestTemperature,
        String hikingActivityStatus
) {
    public CourseScheduleCardResponse(
            CoursePlan coursePlan,
            HikingTime hikingTime,
            Forecast startForecast,
            Forecast arrivalForecast,
            Forecast descentForecast,
            String hikingActivityStatus
    ) {
        this(
                coursePlan.date(),
                hikingTime.startTime().toLocalTime(),
                hikingTime.descentTime().toLocalTime(),
                coursePlan.mountain().name(),
                coursePlan.course().name(),
                coursePlan.course().totalDistance(),
                new ForecastOverview(startForecast),
                new ForecastOverview(arrivalForecast),
                new ForecastOverview(descentForecast),
                arrivalForecast.dailyTemperature().lowestTemperature(),
                arrivalForecast.dailyTemperature().highestTemperature(),
                hikingActivityStatus
        );
    }
}
