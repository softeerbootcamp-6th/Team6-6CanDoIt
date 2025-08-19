package com.softeer.batch.forecast.shortterm.processor;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.common.KmaApiCaller;
import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ShortForecastProcessor implements ItemProcessor<Grid, ShortForecastList> {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final KmaApiCaller<ShortForecastItem> kmaApiCaller;

    private final ForecastMapper forecastMapper;

    @Value("${kma.api.key.short}")
    private String serviceKey;

    @Override
    public ShortForecastList process(Grid grid) {
        ApiTime apiTime = ApiTimeUtil.getShortBaseTime(LocalDateTime.now());

        ShortForecastApiRequest request = new ShortForecastApiRequest(
                serviceKey,
                1,
                1500,
                "json",
                apiTime.baseDate(),
                apiTime.baseTime(),
                grid.x(),
                grid.y()
        );
        return processApiResponse(grid, kmaApiCaller.call(request));
    }

    private ShortForecastList processApiResponse(Grid grid, List<ShortForecastItem> items) {
        Map<LocalDateTime, List<ShortForecastItem>> groupedByTime = items.stream()
                .collect(Collectors.groupingBy(item -> {
                    String forecastDate = item.forecastDate();
                    String forecastTime = item.forecastTime();

                    if (forecastDate == null || forecastTime == null) {
                        log.warn("forecastDate or forecastTime is null for gridId: {}. Skipping item.", grid.id());
                    }

                    return LocalDateTime.of(
                            LocalDate.parse(forecastDate.trim(), dateFormatter),
                            LocalTime.parse(forecastTime.trim(), timeFormatter)
                    );
                }));

        List<Forecast> hourlyForecasts = new ArrayList<>();

        for (Map.Entry<LocalDateTime, List<ShortForecastItem>> entry : groupedByTime.entrySet()) {
            List<ShortForecastItem> shortForecastItems = entry.getValue();

            hourlyForecasts.add(createForecastObject(entry.getKey(), shortForecastItems));
        }

        hourlyForecasts.sort(Comparator.comparing(Forecast::dateTime));
        return new ShortForecastList(grid.id(), hourlyForecasts);
    }

    private Forecast createForecastObject(LocalDateTime dateTime, List<ShortForecastItem> items) {
        Map<String, String> forecastData = items.stream()
                .collect(Collectors.toMap(
                        ShortForecastItem::category,
                        item -> item.forecastValue().trim(),
                        (value1, value2) -> value1
                ));

        String pcpValue = forecastData.getOrDefault("PCP", "강수없음");
        String snoValue = forecastData.getOrDefault("SNO", "적설없음");
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
                safeParseDouble(forecastData.get("TMP")),
                safeParseDouble(forecastData.get("REH")),
                windDir,
                safeParseDouble(forecastData.get("WSD")),
                pty,
                pcpValue,
                safeParseDouble(forecastData.get("POP")),
                snoValue,
                safeParseDouble(forecastData.get("TMX")),
                safeParseDouble(forecastData.get("TMN"))
        );
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
