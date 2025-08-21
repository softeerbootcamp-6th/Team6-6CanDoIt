package com.softeer.batch.forecast.mountain.dto;

import com.softeer.domain.Forecast;
import com.softeer.domain.SunTime;

import java.time.LocalTime;
import java.util.List;

public record MountainDailyForecast(
        long mountainId,
        int gridId,
        List<DailySunTime> dailySunTimes,
        List<Forecast> hourlyForecasts
) {
}
