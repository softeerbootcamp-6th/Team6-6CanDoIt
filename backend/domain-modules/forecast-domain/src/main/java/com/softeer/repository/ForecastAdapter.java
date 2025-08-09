package com.softeer.repository;

import com.softeer.domain.Forecast;

import java.time.LocalDateTime;
import java.util.List;

public interface ForecastAdapter {
    List<Forecast> findForecastsAfterStartTime(int gridId, LocalDateTime startTime);
}
