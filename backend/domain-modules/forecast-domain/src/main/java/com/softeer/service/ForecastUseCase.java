package com.softeer.service;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;

import java.util.List;

public interface ForecastUseCase {
    List<Forecast> findForecastsFromNow(Grid grid);
}
