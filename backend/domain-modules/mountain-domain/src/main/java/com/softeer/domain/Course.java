package com.softeer.domain;

import com.softeer.entity.enums.Level;

public record Course(
        long id,
        String name,
        double totalDistance,
        double totalDuration,
        int altitude,
        Level level,
        String imageUrl,
        Grid startGrid,
        Grid destinationGrid
) {
}
