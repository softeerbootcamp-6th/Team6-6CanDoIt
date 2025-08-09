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

    private final ForecastQuerydslRepository forecastQuerydslRepository;

    @Override
    public List<Forecast> findForecastsAfterStartTime(int gridId, LocalDateTime startTime) {
        return forecastQuerydslRepository.findForecastsAfterStartDateTime(gridId, startTime);
    }
}
