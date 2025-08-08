package com.softeer.domain;

import com.softeer.activity.HikingActivityCalculator;
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
        PrecipitationCondition precipitationCondition,
        DailyTemperature dailyTemperature
) {

    public Forecast(long id, LocalDateTime dateTime, ForecastType forecastType, Sky sky, double temperature,
                    double humidity, WindDirection windDir, double windSpeed, PrecipitationType precipitationType,
                    String precipitation, double precipitationProbability, double snowAccumulation,
                    double highestTemperature, double lowestTemperature
    ) {
        this(id, dateTime, forecastType,
                new SkyCondition(sky),
                new TemperatureCondition(temperature),
                new HumidityCondition(humidity),
                new WindCondition(windDir, windSpeed),
                new PrecipitationCondition(precipitationType, precipitation, precipitationProbability, snowAccumulation),
                new DailyTemperature(highestTemperature, lowestTemperature)
        );
    }

    public String getHikingActivityStatus() {
        return HikingActivityCalculator.calculateHikingActivity(
                temperatureCondition.temperature(),
                dailyTemperature.dailyTemperatureRange(),
                precipitationCondition.precipitation(),
                windCondition.windSpeed(),
                humidityCondition.humidity()
        );
    }
}