package com.softeer.domain.condition;

import com.softeer.domain.Displayable;

import java.util.Arrays;
import java.util.List;

public record HumidityCondition(double humidity) implements Displayable {

    @Override
    public String displayDescription() {
        return HumidityStatus.displayDescription(humidity);
    }

    private enum HumidityStatus {

        DRY("건조해요", 0.0, 39.9),
        PLEASANT("쾌적해요", 40.0, 59.9),
        HUMID("습해요", 60.0, 79.9),
        VERY_HUMID("매우 습해요", 80.0, 100.0);

        private static final List<HumidityStatus> humidityStatuses = Arrays.asList(HumidityStatus.values());

        private final String description;
        private final double lowerBound;
        private final double upperBound;

        HumidityStatus(String description, double lowerBound, double upperBound) {
            this.description = description;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        private boolean isInRange(double value) {
            return value >= lowerBound && value <= upperBound;
        }


        public static String displayDescription(double humidity) {
            for (HumidityStatus status : humidityStatuses) {
                if (status.isInRange(humidity)) {
                    return status.description;
                }
            }
            //TODO CustomException
            throw new IllegalArgumentException("Invalid humidity value: " + humidity);
        }
    }
}