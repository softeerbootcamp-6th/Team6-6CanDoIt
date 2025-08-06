package com.softeer.domain;

import com.softeer.entity.enums.Level;

import java.time.LocalTime;

public record Course(
        long id,
        String name,
        double totalDistance,
        int totalDuration,
        Level level,
        SunTime sunTime,
        String mountainName
) {
    public Course(
            long id,
            String name,
            double totalDistance,
            int totalDuration,
            Level level,
            LocalTime sunrise,
            LocalTime sunset,
            String mountainName
    ) {
        this(
                id,
                name,
                totalDistance,
                totalDuration,
                level,
                new SunTime(sunrise, sunset),
                mountainName
        );
    }
}
