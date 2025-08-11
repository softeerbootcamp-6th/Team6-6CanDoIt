package com.softeer.service.impl;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.ForecastType;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.ForecastException;
import com.softeer.repository.ForecastAdapter;
import com.softeer.service.ForecastUseCase;
import com.softeer.time.HikingTime;
import com.softeer.time.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastUseCaseImpl implements ForecastUseCase {

    private static final long THREE_DAYS = 3L;

    private final ForecastAdapter forecastAdapter;

    @Override
    public List<Forecast> findForecastsFromStartDateTime(Grid grid, LocalDateTime startDateTime) {
        LocalDateTime baseDateTime = TimeUtil.getBaseTime(startDateTime);
        LocalDateTime endDateTime = baseDateTime.plusDays(THREE_DAYS);

        return forecastAdapter.findForecastsBetweenDateTime(grid.id(), baseDateTime, endDateTime);
    }

    @Override
    public CourseForecast findForecastsByHikingTime(Grid grid, HikingTime hikingTime) {
        Forecast startForecast = findForecast(grid, ForecastType.SHORT, hikingTime.startTime());
        Forecast arrivalForecast = findForecast(grid, ForecastType.MOUNTAIN, hikingTime.arrivalTime());
        Forecast descentForecast = findForecast(grid, ForecastType.SHORT, hikingTime.descentTime());
        return new CourseForecast(startForecast, arrivalForecast, descentForecast);
    }

    private Forecast findForecast(Grid grid, ForecastType forecastType, LocalDateTime dateTime) {
        return forecastAdapter.findForecastByTypeAndDateTime(grid.id(), forecastType, dateTime).orElseThrow(
                () -> ExceptionCreator.create(ForecastException.NOT_FOUND,
                        "Forecast not found! Grid : " + grid + ", Type : " + forecastType + " ,  DateTime : " + dateTime));

    }
}
