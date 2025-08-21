package com.softeer.batch.forecast.shortterm.processor;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
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
import com.softeer.throttle.manager.SimpleRetryHandler;
import com.softeer.time.ApiTime;
import com.softeer.time.ApiTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.softeer.batch.forecast.shortterm.config.ShortForecastJobConfig.SHORT_SIMPLE_RETRY_HANDLER;

@Slf4j
@Component
@StepScope
public class ShortForecastProcessor implements ItemProcessor<Grid, ShortForecastList> {

    private static final String SHORT_FORECAST = "SHORT_FORECAST";

    private final ShortForecastApiCaller kmaApiCaller;
    private final ForecastMapper forecastMapper;
    private final SimpleRetryHandler simpleRetryHandler;

    public ShortForecastProcessor(ShortForecastApiCaller kmaApiCaller,
                                  ForecastMapper forecastMapper,
                                  @Qualifier(SHORT_SIMPLE_RETRY_HANDLER) SimpleRetryHandler simpleRetryHandler) {
        this.kmaApiCaller = kmaApiCaller;
        this.forecastMapper = forecastMapper;
        this.simpleRetryHandler = simpleRetryHandler;
    }

    @Value("${kma.api.key.short}")
    private String serviceKey;

    @Override
    public ShortForecastList process(Grid grid) {
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

        return simpleRetryHandler.submit(SHORT_FORECAST, () -> processApiResponse(grid, kmaApiCaller.call(request)));
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

    private double safeParseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse double value: '{}'. Defaulting to 0.0.", value);
            return 0.0;
        }
    }
}
