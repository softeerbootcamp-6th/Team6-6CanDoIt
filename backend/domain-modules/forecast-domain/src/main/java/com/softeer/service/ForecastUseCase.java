package com.softeer.service;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;

import java.time.LocalDateTime;
import java.util.List;

public interface ForecastUseCase {
    List<Forecast> getDailyForecast(Grid grid, LocalDateTime startDateTime);
}
