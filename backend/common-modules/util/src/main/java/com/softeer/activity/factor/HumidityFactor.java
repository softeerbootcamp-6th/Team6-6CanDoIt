package com.softeer.activity.factor;

import java.util.Arrays;
import java.util.List;

public enum HumidityFactor  {

    VERY_DRY       (1, 0, 10.0),
    MODERATE_DRY   (3, 10.0,                  40.0),
    COMFORTABLE    (4, 40.0,                  70.0),
    VERY_HUMID     (1, 70.0,  100);

    private final int score;
    private final double minExclusive;
    private final double maxInclusive;

    private static final List<HumidityFactor> humidityFactors = Arrays.asList(values());

    HumidityFactor(int score, double minExclusive, double maxInclusive) {
        this.score = score;
        this.minExclusive = minExclusive;
        this.maxInclusive = maxInclusive;
    }

    private boolean contains(double humidity) {
        return humidity > minExclusive && humidity <= maxInclusive;
    }

    public static int calculateFactor(double humidity) {
        for (HumidityFactor factor : humidityFactors) {
            if (factor.contains(humidity)) {
                return factor.score;
            }
        }
        throw new IllegalArgumentException("정의되지 않은 습도 범위: " + humidity);
    }
}
