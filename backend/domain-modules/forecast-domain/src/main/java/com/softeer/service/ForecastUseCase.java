package com.softeer.service;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.time.HikingTime;

import java.time.LocalDateTime;
import java.util.List;

public interface ForecastUseCase {
    List<Forecast> findForecastsFromStartDateTime(Grid grid, LocalDateTime startDateTime);

    CourseForecast findForecastsByHikingTime(Grid startGrid, Grid destinationGrid, HikingTime hikingTime);

    record CourseForecast(Forecast startForecast, Forecast arrivalForecast, Forecast descentForecast) {}

    WeatherCondition findForecastWeatherCondition(Grid grid, LocalDateTime dateTime);

    record WeatherCondition(PrecipitationType precipitationType, Sky sky, double surfaceTemperature, double topTemperature, String hikingActivityStatus) {
        public WeatherCondition(Forecast surfaceForecast, Forecast topForecast) {
            this(topForecast.precipitationCondition().precipitationType(), topForecast.skyCondition().sky(),
                    surfaceForecast.temperatureCondition().temperature(), topForecast.temperatureCondition().temperature(), topForecast.getHikingActivityStatus()
            );
        }
    }
}
