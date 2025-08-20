package com.softeer.batch.forecast.ultra.v1;

import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponse;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import com.softeer.shortterm.UltraForecastApiCaller;
import com.softeer.shortterm.dto.request.ShortForecastApiRequest;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import com.softeer.time.ApiTime;
import com.softeer.time.ApiTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.UnknownContentTypeException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class UltraForecastV1Processor implements ItemProcessor<Grid, UltraForecastResponseList> {

    private final UltraForecastApiCaller kmaApiCaller;
    private final ForecastMapper forecastMapper;

    @Value("${kma.api.key.short}")
    private String serviceKey;

    @Value("#{jobParameters[dateTime]}")
    private LocalDateTime dateTime;

    @Override
    public UltraForecastResponseList process(Grid grid) {
        ApiTime ultraBaseTime = ApiTimeUtil.getUltraBaseTime(dateTime);

        ShortForecastApiRequest request = new ShortForecastApiRequest(
                serviceKey,
                1,
                1500,
                "JSON",
                ultraBaseTime.baseDate(),
                ultraBaseTime.baseTime(),
                grid.x(),
                grid.y()
        );
        List<ShortForecastItem> items;
        try {
            items = kmaApiCaller.call(request);
        } catch (UnknownContentTypeException e) {
            log.error("grid {} {}", grid.x(), grid.y());
            throw e;
        }
        return processApiResponse(grid, items);
    }

    private UltraForecastResponseList processApiResponse(Grid grid, List<ShortForecastItem> items) {


        Map<LocalDateTime, List<ShortForecastItem>> groupedByTime = items.stream()
                .collect(Collectors.groupingBy(item -> LocalDateTime.of(
                        item.forecastDate(),
                        item.forecastTime()
                )));

        List<UltraForecastResponse> hourlyForecasts = new ArrayList<>();

        for (Map.Entry<LocalDateTime, List<ShortForecastItem>> entry : groupedByTime.entrySet()) {
            List<ShortForecastItem> shortForecastItems = entry.getValue();

            hourlyForecasts.add(createForecastObject(grid.id(), entry.getKey(), shortForecastItems));
        }

        return new UltraForecastResponseList(hourlyForecasts);
    }

    private UltraForecastResponse createForecastObject(int gridId, LocalDateTime dateTime, List<ShortForecastItem> items) {
        Map<String, String> forecastData = items.stream()
                .collect(Collectors.toMap(
                        ShortForecastItem::category,
                        item -> item.forecastValue().trim(),
                        (value1, value2) -> value1
                ));

        String pcpValue = getValueOrDefault(forecastData, "RN1", "강수없음");
        String skyCode = forecastData.get("SKY");
        String vecCode = forecastData.get("VEC");

        Sky sky = forecastMapper.mapSky(skyCode);
        WindDirection windDir = forecastMapper.mapWindDirection(vecCode);
        PrecipitationType pty = forecastMapper.mapPrecipitationType(forecastData.get("PTY"));

        return new UltraForecastResponse(gridId, dateTime,
                Double.parseDouble(forecastData.get("T1H")), pcpValue,
                sky, Double.parseDouble(forecastData.get("REH")),
                pty, windDir, Double.parseDouble(forecastData.get("WSD"))
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
