package com.softeer.service;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.time.HikingTime;

import java.time.LocalDateTime;
import java.util.List;

public interface ForecastUseCase {
    List<Forecast> findForecastsFromStartDateTime(Grid grid, LocalDateTime startDateTime);
    CourseForecast findForecastsByHikingTime(Grid grid, HikingTime hikingTime);
    record CourseForecast(Forecast startForecast, Forecast arrivalForecast, Forecast descentForecast) {}
}
