package com.softeer.activity.factor;

import java.util.Arrays;
import java.util.List;

public enum TemperatureFactor  {

    DANGER_LOW   (1, Double.NEGATIVE_INFINITY, -6.0),   // 위험 (저온)
    LEVEL_2      (2, -6.0, 4.0),
    LEVEL_3      (3, 4.0, 14.0),
    SAFE         (4, 14.0, 26.0),                       // 안전
    DANGER_HIGH  (1, 26.0, Double.POSITIVE_INFINITY);   // 위험 (고온)

    private final int score;
    private final double minExclusive;
    private final double maxInclusive;

    TemperatureFactor(int score, double minExclusive, double maxInclusive) {
        this.score = score;
        this.minExclusive = minExclusive;
        this.maxInclusive = maxInclusive;
    }

    private static final List<TemperatureFactor> temperatureFactors = Arrays.asList(TemperatureFactor.values());

    private boolean contains(double temperature) {
        return temperature > minExclusive && temperature <= maxInclusive;
    }

    public static int calculateFactor(double temperature) {
        for (TemperatureFactor factor : temperatureFactors) {
            if (factor.contains(temperature)) {
                return factor.score;
            }
        }
        throw new IllegalArgumentException("정의되지 않은 온도 범위: " + temperature);
    }
}
