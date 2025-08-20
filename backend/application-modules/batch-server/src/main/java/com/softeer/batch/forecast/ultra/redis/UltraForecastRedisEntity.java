package com.softeer.batch.forecast.ultra.redis;

import com.softeer.batch.forecast.ultra.dto.UltraForecastResponse;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;

public record UltraForecastRedisEntity(double temperature, String precipitation,
                                       Sky sky, double humidity, PrecipitationType precipitationType,
                                       WindDirection windDirection, double windSpeed) {

    public UltraForecastRedisEntity(UltraForecastResponse response) {
        this(
                response.temperature(), response.precipitation(),
                response.sky(), response.humidity(), response.precipitationType(),
                response.windDirection(), response.windSpeed()
        );
    }
}
