package com.softeer.batch.forecast.shortterm.processor;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.forecast.shortterm.listener.DailyTemperatureCollector;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.domain.DailyTemperature;
import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import com.softeer.shortterm.ShortForecastApiCaller;
import com.softeer.shortterm.dto.request.ShortForecastApiRequest;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import com.softeer.throttle.manager.AbstractThrottlingManager;
import com.softeer.time.ApiTime;
import com.softeer.time.ApiTimeUtil;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static com.softeer.batch.common.support.BatchNames.Steps.SHORT_FORECAST_V2_EX_SERVICE;
import static com.softeer.batch.common.support.BatchNames.Steps.SHORT_LEAKY_MANAGER;

@Component
@StepScope
public class ShortForecastV2Processor implements ItemProcessor<Grid, CompletableFuture<ShortForecastList>> {

    private static final String SHORT_FORECAST = "SHORT_FORECAST";

    private final ShortForecastApiCaller kmaApiCaller;
    private final ForecastMapper forecastMapper;
    private final AbstractThrottlingManager throttlingManager;
    private final ExecutorService executorService;
    private final DailyTemperatureCollector dailyTemperatureCollector;

    public ShortForecastV2Processor(ShortForecastApiCaller kmaApiCaller,
                                  ForecastMapper forecastMapper,
                                  @Qualifier(SHORT_LEAKY_MANAGER) AbstractThrottlingManager throttlingManager,
                                  @Qualifier(SHORT_FORECAST_V2_EX_SERVICE) ExecutorService executorService,
                                  DailyTemperatureCollector dailyTemperatureCollector
    ) {
        this.kmaApiCaller = kmaApiCaller;
        this.forecastMapper = forecastMapper;
        this.throttlingManager = throttlingManager;
        this.executorService = executorService;
        this.dailyTemperatureCollector = dailyTemperatureCollector;
    }

    @Value("${kma.api.key.short}")
    private String serviceKey;

    @Override
    public CompletableFuture<ShortForecastList> process(Grid grid) {
        ApiTime apiTime = ApiTimeUtil.getShortBaseTime(LocalDateTime.now());

        ShortForecastApiRequest request = new ShortForecastApiRequest(
                serviceKey,
                1,
                1500,
                "JSON",
                apiTime.baseDate(),
                apiTime.baseTime(),
                grid.x(),
                grid.y()
        );


        return throttlingManager.submit(SHORT_FORECAST, () -> CompletableFuture
                .supplyAsync(() -> kmaApiCaller.call(request), executorService)
                .thenApply(response -> processApiResponse(grid, response)));
    }

    private ShortForecastList processApiResponse(Grid grid, List<ShortForecastItem> items) {
        Map<LocalDate, List<ShortForecastItem>> temperatureMap = items.stream()
                .filter(item -> item.category().equals("TMX") || item.category().equals("TMN"))
                .collect(Collectors.groupingBy(ShortForecastItem::forecastDate));

        Map<LocalDate, DailyTemperature> dailyTemperatureMap = new HashMap<>();

        for (Map.Entry<LocalDate, List<ShortForecastItem>> temperatureEntry : temperatureMap.entrySet()) {
            List<ShortForecastItem> temperatureValues = temperatureEntry.getValue();
            if(temperatureValues.size() != 2) continue;

            temperatureValues.sort(Comparator.comparingDouble(item -> Double.parseDouble(item.forecastValue())));
            ShortForecastItem lowestTemperatureItem = temperatureValues.get(0);
            ShortForecastItem highestTemperatureItem = temperatureValues.get(1);

            DailyTemperature dailyTemperature = new DailyTemperature(Double.parseDouble(highestTemperatureItem.forecastValue()), Double.parseDouble(lowestTemperatureItem.forecastValue()));
            dailyTemperatureMap.put(temperatureEntry.getKey(), dailyTemperature);
        }

        Map<LocalDateTime, List<ShortForecastItem>> groupedByTime = items.stream()
                .collect(Collectors.groupingBy(item -> LocalDateTime.of(
                        item.forecastDate(),
                        item.forecastTime()
                )));

        List<Forecast> hourlyForecasts = new ArrayList<>();

        for (Map.Entry<LocalDateTime, List<ShortForecastItem>> entry : groupedByTime.entrySet()) {
            List<ShortForecastItem> shortForecastItems = entry.getValue();
            LocalDate date = LocalDate.from(entry.getKey());

            if (!dailyTemperatureMap.containsKey(date)) continue;
            hourlyForecasts.add(createForecastObject(entry.getKey(), shortForecastItems, dailyTemperatureMap.get(date)));

            dailyTemperatureCollector.collect(
                    grid.id(),
                    date,
                    dailyTemperatureMap.get(date).highestTemperature(),
                    dailyTemperatureMap.get(date).lowestTemperature()
            );
        }

        hourlyForecasts.sort(Comparator.comparing(Forecast::dateTime));
        return new ShortForecastList(grid.id(), hourlyForecasts);
    }

    private Forecast createForecastObject(LocalDateTime dateTime, List<ShortForecastItem> items, DailyTemperature dailyTemperature) {
        Map<String, String> forecastData = items.stream()
                .collect(Collectors.toMap(
                        ShortForecastItem::category,
                        item -> item.forecastValue().trim(),
                        (value1, value2) -> value1
                ));

        String pcpValue = getValueOrDefault(forecastData, "PCP", "강수없음");
        String snoValue = getValueOrDefault(forecastData, "SNO", "적설없음");
        String skyCode = forecastData.getOrDefault("SKY", "3");
        String vecCode = forecastData.getOrDefault("VEC", "0");

        Sky sky = forecastMapper.mapSky(skyCode);
        WindDirection windDir = forecastMapper.mapWindDirection(vecCode);
        PrecipitationType pty = forecastMapper.deriveMountainPrecipitationType(pcpValue, snoValue);

        return new Forecast(
                0L,
                dateTime,
                ForecastType.SHORT,
                sky,
                Double.parseDouble(forecastData.get("TMP")),
                Double.parseDouble(forecastData.get("REH")),
                windDir,
                Double.parseDouble(forecastData.get("WSD")),
                pty,
                pcpValue,
                Double.parseDouble(forecastData.get("POP")),
                snoValue,
                dailyTemperature.highestTemperature(),
                dailyTemperature.lowestTemperature()
        );
    }

    private String getValueOrDefault(Map<String, String> map, String key, String defaultValue) {
        String value = map.get(key);
        if (value == null || value.trim().equals("0") || value.trim().equals("0.0")) {
            return defaultValue;
        }
        return value;
    }

}
