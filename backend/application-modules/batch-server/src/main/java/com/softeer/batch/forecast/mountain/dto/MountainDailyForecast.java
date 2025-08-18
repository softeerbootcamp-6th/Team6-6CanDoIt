package com.softeer.batch.forecast.mountain.dto;

import com.softeer.domain.Forecast;
import com.softeer.domain.SunTime;

import java.time.LocalTime;
import java.util.List;

public record MountainDailyForecast(
        long mountainId,
        long gridId,
        SunTime sunTime,
        List<Forecast> hourlyForecasts
) {

    public MountainDailyForecast(
            long mountainId,
            long gridId,
            LocalTime sunrise,
            LocalTime sunset,
            List<Forecast> hourlyForecasts
    ) {
        this(
                mountainId,
                gridId,
                new SunTime(sunrise, sunset),
                hourlyForecasts
        );
    }
}
