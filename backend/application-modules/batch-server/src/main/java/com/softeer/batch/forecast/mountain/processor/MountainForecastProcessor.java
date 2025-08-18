package com.softeer.batch.forecast.mountain.processor;

import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.batch.forecast.mountain.dto.MountainIdentifier;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.common.KmaApiCaller;
import com.softeer.domain.Forecast;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import com.softeer.mountain.dto.MountainForecastApiRequest;
import com.softeer.mountain.dto.MountainForecastApiResponse;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class MountainForecastProcessor implements ItemProcessor<MountainIdentifier, MountainDailyForecast> {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter sunTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final KmaApiCaller<MountainForecastApiResponse> kmaApiCaller;

    private final ForecastMapper forecastMapper;

    @Value("${kma.api.key.mountain}")
    private String authKey;

    @Override
    public MountainDailyForecast process(MountainIdentifier identifier) {

        log.info(">>>>> Processing forecast for mountain ID: {}", identifier.id());

        ApiTime apiTime = ApiTimeUtil.getShortBaseTime(LocalDateTime.now());

        MountainForecastApiRequest request = new MountainForecastApiRequest(
                authKey,
                identifier.code(),
                apiTime.baseDate(),
                apiTime.baseTime()
        );

        List<MountainForecastApiResponse> items = kmaApiCaller.call(request);

        return processApiResponse(identifier, items);
    }

    private MountainDailyForecast processApiResponse(MountainIdentifier identifier, List<MountainForecastApiResponse> items) {
        if (items == null || items.isEmpty()) {
            LocalTime sunriseTime = LocalTime.parse("06:00", sunTimeFormatter);
            LocalTime sunsetTime = LocalTime.parse("18:00", sunTimeFormatter);
            return new MountainDailyForecast(identifier.id(), identifier.gridId(), sunriseTime, sunsetTime, Collections.emptyList());
        }

        Map<LocalDateTime, List<MountainForecastApiResponse>> groupedByTime = items.stream()
                .collect(Collectors.groupingBy(item -> {
                    String forecastDate = item.forecastDate();
                    String forecastTime = item.forecastTime();

                    if (forecastDate == null || forecastTime == null) {
                        log.warn("forecastDate or forecastTime is null for mountainId: {}. Skipping item.", identifier.id());
                    }

                    return LocalDateTime.of(
                            LocalDate.parse(forecastDate.trim(), dateFormatter),
                            LocalTime.parse(forecastTime.trim(), timeFormatter)
                    );
                }));

        List<Forecast> hourlyForecasts = new ArrayList<>();
        LocalTime sunriseTime = null;
        LocalTime sunsetTime = null;

        for (Map.Entry<LocalDateTime, List<MountainForecastApiResponse>> entry : groupedByTime.entrySet()) {
            List<MountainForecastApiResponse> hourlyItems = entry.getValue();

            if (isActualForecast(hourlyItems)) {
                hourlyForecasts.add(createForecastObject(entry.getKey(), hourlyItems));
            } else {
                for (MountainForecastApiResponse item : hourlyItems) {
                    String category = item.category().trim();
                    String value = item.forecastValue().trim();

                    if ("SRE".equals(category)) {
                        sunriseTime = LocalTime.parse(value, sunTimeFormatter);
                    } else if ("SSE".equals(category)) {
                        sunsetTime = LocalTime.parse(value, sunTimeFormatter);
                    }
                }
            }
        }

        hourlyForecasts.sort(Comparator.comparing(Forecast::dateTime));
        return new MountainDailyForecast(identifier.id(), identifier.gridId(), sunriseTime, sunsetTime, hourlyForecasts);
    }

    private Forecast createForecastObject(LocalDateTime dateTime, List<MountainForecastApiResponse> hourlyItems) {
        Map<String, String> forecastData = hourlyItems.stream()
                .collect(Collectors.toMap(
                        MountainForecastApiResponse::category,
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

        return new Forecast(0L, dateTime, ForecastType.MOUNTAIN, sky,
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

    private boolean isActualForecast(List<MountainForecastApiResponse> hourlyItems) {
        return hourlyItems.stream().anyMatch(item -> "TMP".equals(item.category()));
    }

    private double safeParseDouble(String value) {
        if (value == null || value.trim().isEmpty() || !Character.isDigit(value.charAt(0))) {
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