package com.softeer.domain;

public record DailyTemperature(double highestTemperature,  double lowestTemperature) {

    public double dailyTemperatureRange() {
        return highestTemperature - lowestTemperature;
    }

}
