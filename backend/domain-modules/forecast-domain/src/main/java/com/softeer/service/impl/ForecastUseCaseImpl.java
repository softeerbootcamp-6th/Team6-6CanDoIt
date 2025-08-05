package com.softeer.service.impl;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.repository.ForecastAdapter;
import com.softeer.service.ForecastUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastUseCaseImpl implements ForecastUseCase {

    private final ForecastAdapter forecastAdapter;

    @Override
    public List<Forecast> getDailyForecast(final Grid grid, final LocalDateTime startDateTime) {
        return forecastAdapter.findForecastsFor24Hours(grid.id(), startDateTime);
    }
}
