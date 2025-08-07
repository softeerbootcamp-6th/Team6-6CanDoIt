package com.softeer.activity.factor;

import java.util.Arrays;
import java.util.List;

public enum DailyTemperatureRangeFactor {

    SMALL   (4, 0.0,    7.0),                   // 일교차 0 ≤ x < 7.0
    MODERATE(3, 7.0,    9.3),                   // 일교차 7.0 ≤ x < 9.3
    LARGE   (2, 9.3,   16.2),                   // 일교차 9.3 ≤ x < 16.2
    EXTREME (1, 16.2, Double.POSITIVE_INFINITY); // 일교차 16.2 ≤ x

    private final int score;
    private final double minInclusive;
    private final double maxExclusive;

    private static final List<DailyTemperatureRangeFactor> factors =  Arrays.asList(DailyTemperatureRangeFactor.values());

    DailyTemperatureRangeFactor(int score, double minInclusive, double maxExclusive) {
        this.score = score;
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
    }

    private boolean contains(double range) {
        return range >= minInclusive && range < maxExclusive;
    }

    public static int calculateFactor(double dailyTemperatureRange) {
        for (DailyTemperatureRangeFactor f : factors) {
            if (f.contains(dailyTemperatureRange)) {
                return f.score;
            }
        }
        throw new IllegalArgumentException("정의되지 않은 일교차 범위: " + dailyTemperatureRange);
    }
}
