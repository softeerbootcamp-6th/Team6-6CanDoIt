package com.softeer.domain;

import com.softeer.entity.enums.Level;

public record Course(
        long id,
        String name,
        double totalDistance,
        int totalDuration,
        Level level,
        String mountainName
) {
}
