package com.softeer.dto.response.card;

import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.service.ForecastUseCase;

public record WeatherMetric(PrecipitationType precipitationType, Sky sky, double surfaceTemperature, double topTemperature) {
    public WeatherMetric(ForecastUseCase.WeatherCondition weatherCondition) {
        this(weatherCondition.precipitationType(), weatherCondition.sky(), weatherCondition.surfaceTemperature(), weatherCondition.topTemperature());
    }
}