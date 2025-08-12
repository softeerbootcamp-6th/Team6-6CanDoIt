package com.softeer.batch.mountain.dto;

public record MountainForecastApiRequest(
        int mountainNum,
        String baseDate,
        String baseTime,
        String authKey
) {
}
