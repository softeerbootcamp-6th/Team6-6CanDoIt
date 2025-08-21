package com.softeer.dto.response.card;

import com.softeer.domain.Forecast;

public record ForecastOverview(
        double temperature,
        double windSpeed,
        double apparentTemperature,
        double precipitationProbability,
        String sky,
        double humidity
) {
    public ForecastOverview(
            Forecast forecast
    ) {
        this(
                forecast.temperatureCondition().temperature(),
                forecast.windCondition().windSpeed(),
                forecast.temperatureCondition().apparentTemperature(),
                forecast.precipitationCondition().precipitationProbability(),
                forecast.skyCondition().sky().getDisplayDescription(),
                forecast.humidityCondition().humidity()
        );
    }
}
