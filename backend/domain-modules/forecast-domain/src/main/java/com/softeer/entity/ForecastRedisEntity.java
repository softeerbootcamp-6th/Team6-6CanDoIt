package com.softeer.entity;

import com.softeer.domain.Forecast;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;

import java.time.LocalDateTime;

public record ForecastRedisEntity(long id, LocalDateTime dateTime, ForecastType forecastType, Sky sky, double temperature,
                                  double humidity, WindDirection windDir, double windSpeed, PrecipitationType precipitationType,
                                  String precipitation, double precipitationProbability, String snowAccumulation,
                                  double highestTemperature, double lowestTemperature, int gridId) implements Comparable<ForecastRedisEntity> {

    public static Forecast toDomain(ForecastRedisEntity entity) {
        return new Forecast(entity.id, entity.dateTime, entity.forecastType, entity.sky, entity.temperature,
                entity.humidity, entity.windDir, entity.windSpeed, entity.precipitationType,
                entity.precipitation, entity.precipitationProbability, entity.snowAccumulation,
                entity.highestTemperature, entity.lowestTemperature
        );
    }

    @Override
    public int compareTo(ForecastRedisEntity o) {
        return dateTime.compareTo(o.dateTime);
    }
}
