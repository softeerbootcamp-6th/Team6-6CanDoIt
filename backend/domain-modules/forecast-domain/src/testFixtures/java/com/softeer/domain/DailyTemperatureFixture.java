package com.softeer.domain;

public class DailyTemperatureFixture {

    public static final long DEFAULT_ID = 0;

    public static DailyTemperatureBuilder builder() {
        return new DailyTemperatureBuilder();
    }

    public static DailyTemperature createDefault() {
        return builder().build();
    }

    public static class DailyTemperatureBuilder {
        private double highestTemperature = 20.0;
        private double lowestTemperature = 10.0;

        public DailyTemperatureBuilder highestTemperature(double highestTemperature) {
            this.highestTemperature = highestTemperature;
            return this;
        }

        public DailyTemperatureBuilder lowestTemperature(double lowestTemperature) {
            this.lowestTemperature = lowestTemperature;
            return this;
        }

        public DailyTemperature build() {
            return new DailyTemperature(highestTemperature, lowestTemperature);
        }
    }
}