package com.softeer.repository.impl;

import com.softeer.domain.Forecast;
import com.softeer.entity.enums.ForecastType;
import com.softeer.repository.ForecastAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ForestAdapterImpl implements ForecastAdapter {

    private final ForecastQuerydslRepository forecastQuerydslRepository;

    @Override
    public List<Forecast> findForecastsBetweenDateTime(int gridId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return forecastQuerydslRepository.findForecastsAfterStartDateTime(gridId, startDateTime, endDateTime);
    }

    @Override
    public Optional<Forecast> findForecastByTypeAndDateTime(int gridId, ForecastType forecastType, LocalDateTime dateTime) {
        return forecastQuerydslRepository.findForecastByTypeAndDateTime(gridId, forecastType, dateTime);
    }

    @Override
    public Map<Integer, Forecast> findForecastsByTypeAndDateTime(List<Integer> gridIds, ForecastType forecastType, LocalDateTime dateTime) {
        return forecastQuerydslRepository.findForecastsByTypeAndDateTime(gridIds, forecastType, dateTime);
    }
}
