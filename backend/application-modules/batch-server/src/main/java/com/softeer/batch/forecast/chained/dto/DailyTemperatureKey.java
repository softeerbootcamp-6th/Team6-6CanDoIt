package com.softeer.batch.forecast.chained.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record DailyTemperatureKey(
        long gridId,
        LocalDate date
) implements Serializable {
}
