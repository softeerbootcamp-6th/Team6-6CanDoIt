package com.softeer.domain.condition;

import com.softeer.domain.Displayable;
import com.softeer.entity.enums.WindDirection;

import java.util.Arrays;
import java.util.List;

public record WindCondition(WindDirection direction, double windSpeed) implements Displayable {

    @Override
    public String displayDescription() {
        return WindSpeedStatus.description(windSpeed) + " " + direction.getDescription();
    }

    private enum WindSpeedStatus {
        WEAK_WIND("약한", 0.0, 3.99),
        MODERATE_WIND("약간 강한", 4.0, 8.99),
        STRONG_WIND("강한", 9.0, Double.MAX_VALUE);

        private static final List<WindSpeedStatus> windSpeedStatuses = Arrays.asList(values());

        private final String description;
        private final double lowerBound;
        private final double upperBound;

        WindSpeedStatus(String description, double lowerBound, double upperBound) {
            this.description = description;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        private boolean isInRange(double value) {
            return value >= lowerBound && value <= upperBound;
        }

        public static String description(double windSpeed) {
            for (WindSpeedStatus status : windSpeedStatuses) {
                if (status.isInRange(windSpeed)) {
                    return status.description;
                }
            }
            throw new IllegalArgumentException("Invalid wind speed value: " + windSpeed);
        }
    }
}