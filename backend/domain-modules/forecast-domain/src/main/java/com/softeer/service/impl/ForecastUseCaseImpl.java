package com.softeer.service.impl;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.ForecastType;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.ForecastException;
import com.softeer.load.RedisSupporter;
import com.softeer.repository.ForecastAdapter;
import com.softeer.service.ForecastUseCase;
import com.softeer.time.HikingTime;
import com.softeer.time.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ForecastUseCaseImpl implements ForecastUseCase {

    private static final long THREE_DAYS = 3L;

    private final RedisSupporter redisSupporter;
    private final ForecastAdapter forecastAdapter;
    private final ForecastRedisQueryService redisQueryService;

    @Override
    public List<Forecast> findForecastsFromStartDateTime(Grid grid, LocalDateTime startDateTime) {
        return redisSupporter.loadListWithFallback(
                () -> redisQueryService.findShortForecasts(grid.id()),
                () ->
                {
                    LocalDateTime baseDateTime = TimeUtil.getBaseTime(startDateTime);
                    LocalDateTime endDateTime = baseDateTime.plusDays(THREE_DAYS);

                    return forecastAdapter.findForecastsBetweenDateTime(grid.id(), baseDateTime, endDateTime);
                }
        );
    }

    @Override
    public CourseForecast findForecastsByHikingTime(Grid startGrid, Grid destinationGrid, HikingTime hikingTime) {
        Forecast startForecast = loadForecastWithRedis(startGrid, ForecastType.SHORT, hikingTime.startTime());
        Forecast arrivalForecast = loadForecastWithRedis(destinationGrid, ForecastType.MOUNTAIN, hikingTime.arrivalTime());
        Forecast descentForecast = loadForecastWithRedis(startGrid, ForecastType.SHORT, hikingTime.descentTime());
        return new CourseForecast(startForecast, arrivalForecast, descentForecast);
    }

    @Override
    public WeatherCondition findForecastWeatherCondition(Grid grid, LocalDateTime dateTime) {
        Forecast surfaceForecast = loadForecastWithRedis(grid, ForecastType.SHORT, dateTime);
        Forecast topForecast = loadForecastWithRedis(grid, ForecastType.MOUNTAIN, dateTime);

        return new WeatherCondition(surfaceForecast, topForecast);
    }

    @Override
    public Map<Integer, WeatherCondition> findAllWeatherConditions(List<Integer> gridIds, LocalDateTime dateTime) {
        Map<Integer, WeatherCondition> weatherConditionMap = new HashMap<>();

        Map<Integer, Forecast> shortForecastMap = redisSupporter.loadMapWithFallback(
                () -> redisQueryService.findForecastsByGridIdAndTypeAndDateTime(gridIds, ForecastType.SHORT, dateTime),
                () -> forecastAdapter.findForecastsByTypeAndDateTime(gridIds, ForecastType.SHORT, dateTime)
        );


        Map<Integer, Forecast> mountainForecastMap = redisSupporter.loadMapWithFallback(
                () -> redisQueryService.findForecastsByGridIdAndTypeAndDateTime(gridIds, ForecastType.MOUNTAIN, dateTime),
                () -> forecastAdapter.findForecastsByTypeAndDateTime(gridIds, ForecastType.MOUNTAIN, dateTime)
        );

        for (Integer gridId : gridIds) {

            Forecast forecast = shortForecastMap.get(gridId);
            Forecast topForecast = mountainForecastMap.get(gridId);
            WeatherCondition weatherCondition = new WeatherCondition(forecast, topForecast);
            weatherConditionMap.put(gridId, weatherCondition);
        }

        return weatherConditionMap;
    }

    private Forecast loadForecastWithRedis(Grid grid, ForecastType forecastType, LocalDateTime dateTime) {
        return redisSupporter.loadObjectWithFallback(
                () -> redisQueryService.findForecastByGridIdAndTypeAndDateTime(grid.id(), forecastType, dateTime),
                () -> findForecast(grid, forecastType, dateTime)
        );
    }

    private Forecast findForecast(Grid grid, ForecastType forecastType, LocalDateTime dateTime) {
        return forecastAdapter.findForecastByTypeAndDateTime(grid.id(), forecastType, dateTime).orElseThrow(
                () -> ExceptionCreator.create(ForecastException.NOT_FOUND,
                        "Forecast not found! Grid : " + grid + ", Type : " + forecastType + " ,  DateTime : " + dateTime));
    }
}
