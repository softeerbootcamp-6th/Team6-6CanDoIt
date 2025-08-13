package com.softeer.repository;

import com.softeer.domain.Forecast;
import com.softeer.entity.enums.ForecastType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ForecastAdapter {
    List<Forecast> findForecastsBetweenDateTime(int gridId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    Optional<Forecast> findForecastByTypeAndDateTime(int gridId, ForecastType forecastType, LocalDateTime dateTime);
    Map<Integer, Forecast> findForecastsByTypeAndDateTime(List<Integer> gridIds, ForecastType forecastType, LocalDateTime dateTime);
}
