package com.softeer.batch.forecast.mountain.dto;

import com.softeer.domain.SunTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record DailySunTime(
        LocalDate date,
        SunTime sunTime
) {
    public DailySunTime(
            LocalDate date,
            LocalTime sunrise,
            LocalTime sunset
    ) {
        this(date, new SunTime(sunrise, sunset));
    }
}
