package com.softeer.batch.forecast.chained.dto;

import java.io.Serializable;

public record DailyTemperatureValue(
        double highest,
        double lowest
) implements Serializable {
}
