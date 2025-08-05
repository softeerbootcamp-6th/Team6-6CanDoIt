package com.softeer.domain;

public record DailyTemperature(int highestTemperature,  int lowestTemperature) {

    public int dailyTemperatureRange() {
        return highestTemperature - lowestTemperature;
    }

}
