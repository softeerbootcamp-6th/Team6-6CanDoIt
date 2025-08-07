package com.softeer.activity.factor;

import java.util.Arrays;
import java.util.List;

public enum WindSpeedFactor {

    CALM       (4, Double.NEGATIVE_INFINITY, 3.4),  // 편안:  x < 3.4
    BREEZE     (3, 3.4,                       5.0),  // 산들바람: 3.4 ≤ x < 5.0
    STRONG_WIND(1, 5.0,      Double.POSITIVE_INFINITY); // 강풍(위험): 5.0 ≤ x

    private final int score;
    private final double minInclusive;
    private final double maxExclusive;

    WindSpeedFactor(int score, double minInclusive, double maxExclusive) {
        this.score = score;
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
    }

    private static final List<WindSpeedFactor> windSpeedFactors = Arrays.asList(WindSpeedFactor.values());

    private boolean contains(double windSpeed) {
        return windSpeed >= minInclusive && windSpeed < maxExclusive;
    }

    public static int calculateFactor(Double windSpeed) {
        for (WindSpeedFactor factor : windSpeedFactors) {
            if (factor.contains(windSpeed)) {
                return factor.score;
            }
        }
        throw new IllegalArgumentException("정의되지 않은 풍속 범위: " + windSpeed);
    }
}
