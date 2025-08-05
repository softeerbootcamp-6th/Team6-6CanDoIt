package com.softeer.domain;

import com.softeer.domain.condition.*;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;

import java.time.LocalDateTime;

public record Forecast(
        long id, LocalDateTime dateTime, ForecastType forecastType,
        SkyCondition skyCondition,
        TemperatureCondition temperatureCondition,
        HumidityCondition humidityCondition,
        WindCondition windCondition,
        PrecipitationCondition precipitationCondition
        ) {

    public Forecast(long id, LocalDateTime dateTime, ForecastType forecastType, Sky sky, double temperature,
                    double humidity, WindDirection windDir, double windSpeed, PrecipitationType precipitationType,
                    int precipitation, double precipitationProbability, double snowAccumulation
    ) {
        this(id, dateTime, forecastType,
                new SkyCondition(sky),
                new TemperatureCondition(temperature),
                new HumidityCondition(humidity),
                new WindCondition(windDir, windSpeed),
                new PrecipitationCondition(precipitationType, precipitation, precipitationProbability, snowAccumulation)
        );
    }

}


