package com.softeer.repository.impl;

import com.softeer.domain.Forecast;
import com.softeer.repository.ForecastAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ForestAdapterImpl implements ForecastAdapter {

    private final ForecastJdbcRepository forecastJdbcRepository;

    @Override
    public List<Forecast> findForecastsFor24Hours(long gridId, LocalDateTime startTime) {
        return forecastJdbcRepository.findForecastsFor24Hours(gridId, startTime);
    }
}
