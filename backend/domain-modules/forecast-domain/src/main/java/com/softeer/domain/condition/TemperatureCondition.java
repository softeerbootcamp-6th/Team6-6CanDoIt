package com.softeer.domain.condition;

import com.softeer.apparent.ApparentTemperatureCalculator;
import com.softeer.domain.Displayable;

import java.util.Arrays;
import java.util.List;

public record TemperatureCondition(double temperature, double apparentTemperature) implements Displayable {

    public TemperatureCondition(double temperature, double windSpeed, double humidity) {
        this(temperature, ApparentTemperatureCalculator.calculateApparentTemperature(temperature, windSpeed, humidity));
    }

    @Override
    public String displayDescription() {
        return TemperatureStatus.getDescription(apparentTemperature);
    }

    private enum TemperatureStatus {
        VERY_COLD("매우 추워요", -100.0, 10.0),
        COLD("추워요", 10.1, 18.0),
        PLEASANT("쾌적해요", 18.1, 25.0),
        HOT("더워요", 25.1, 30.0),
        VERY_HOT("매우 더워요", 30.1, 100.0);

        private static final List<TemperatureStatus> temperatureStatuses = Arrays.asList(values());

        private final String description;
        private final double lowerBound;
        private final double upperBound;

        TemperatureStatus(String description, double lowerBound, double upperBound) {
            this.description = description;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        private boolean isInRange(double value) {
            return value >= lowerBound && value <= upperBound;
        }

        public static String getDescription(double temperature) {
            for (TemperatureStatus status : temperatureStatuses) {
                if (status.isInRange(temperature)) {
                    return status.description;
                }
            }
            throw new IllegalArgumentException("유효하지 않은 온도 값입니다: " + temperature);
        }
    }
}