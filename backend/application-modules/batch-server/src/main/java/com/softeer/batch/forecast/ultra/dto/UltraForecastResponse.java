package com.softeer.batch.forecast.ultra.dto;

import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;

import java.time.LocalDateTime;

public record UltraForecastResponse(int gridId, LocalDateTime dateTime,
                                    double temperature, String precipitation,
                                    Sky sky, double humidity, PrecipitationType precipitationType,
                                    WindDirection windDirection, double windSpeed
                                    ) {
}
