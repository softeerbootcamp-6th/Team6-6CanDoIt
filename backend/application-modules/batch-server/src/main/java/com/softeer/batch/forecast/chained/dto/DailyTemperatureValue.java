package com.softeer.batch.forecast.chained.dto;

import java.io.Serializable;

public record DailyTemperatureValue(
        double highestTemperature,
        double lowestTemperature
) implements Serializable {
}
