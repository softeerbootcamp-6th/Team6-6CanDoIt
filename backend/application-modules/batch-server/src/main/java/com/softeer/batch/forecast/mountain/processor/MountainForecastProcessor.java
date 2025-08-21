package com.softeer.batch.forecast.mountain.processor;

import com.softeer.batch.forecast.mountain.dto.DailySunTime;
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

    public static final int DEFAULT_TEMPERATURE = -999;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter sunTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private static final LocalTime DEFAULT_SUNRISE_TIME = LocalTime.of(5, 32);
    private static final LocalTime DEFAULT_SUNSET_TIME = LocalTime.of(18, 48);

    private static final int MINIMUM_FORECAST_ITEMS = 8;

    private final KmaApiCaller<MountainForecastApiResponse> kmaApiCaller;

    private final ForecastMapper forecastMapper;

    @Value("${kma.api.key.mountain}")
    private String authKey;

    @Override
    public MountainDailyForecast process(MountainIdentifier identifier) {
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
        Map<LocalDateTime, List<MountainForecastApiResponse>> groupedByTime = items.stream()
                .collect(Collectors.groupingBy(item -> {
                    String forecastDate = item.forecastDate();
                    String forecastTime = item.forecastTime();

                    return LocalDateTime.of(
                            LocalDate.parse(forecastDate.trim(), dateFormatter),
                            LocalTime.parse(forecastTime.trim(), timeFormatter)
                    );
                }));

        List<Forecast> hourlyForecasts = new ArrayList<>();
        List<DailySunTime> dailySunTimes = new ArrayList<>();

        for (Map.Entry<LocalDateTime, List<MountainForecastApiResponse>> entry : groupedByTime.entrySet()) {
            List<MountainForecastApiResponse> hourlyItems = entry.getValue();
            DailySunTime dailySunTime = getDailySunTime(entry, hourlyItems);

            dailySunTimes.add(dailySunTime);

            if (hourlyItems.size() >= MINIMUM_FORECAST_ITEMS) {
                hourlyForecasts.add(createForecastObject(entry.getKey(), hourlyItems));
            }
        }

        hourlyForecasts.sort(Comparator.comparing(Forecast::dateTime));

        return new MountainDailyForecast(identifier.id(), identifier.gridId(), dailySunTimes, hourlyForecasts);
    }

    private DailySunTime getDailySunTime(
            Map.Entry<LocalDateTime, List<MountainForecastApiResponse>> entry,
            List<MountainForecastApiResponse> hourlyItems
    ) {
        DailySunTime dailySunTime;
        LocalTime sunriseTime = null;
        LocalTime sunsetTime = null;

        for (MountainForecastApiResponse item : hourlyItems) {
            String category = item.category().trim();
            String value = item.forecastValue().trim();

            if ("SRE".equals(category)) {
                sunriseTime = LocalTime.parse(value, sunTimeFormatter);
            } else if ("SSE".equals(category)) {
                sunsetTime = LocalTime.parse(value, sunTimeFormatter);
            }
        }

        if (sunriseTime != null && sunsetTime != null) {
            dailySunTime = new DailySunTime(entry.getKey().toLocalDate(), sunriseTime, sunsetTime);
        } else {
            dailySunTime = new DailySunTime(entry.getKey().toLocalDate(), DEFAULT_SUNRISE_TIME, DEFAULT_SUNSET_TIME);
        }
        return dailySunTime;
    }

    private Forecast createForecastObject(LocalDateTime dateTime, List<MountainForecastApiResponse> hourlyItems) {
        Map<String, String> forecastData = hourlyItems.stream()
                .collect(Collectors.toMap(
                        MountainForecastApiResponse::category,
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
                ForecastType.MOUNTAIN,
                sky,
                Double.parseDouble(forecastData.get("TMP")),
                Double.parseDouble(forecastData.get("REH")),
                windDir,
                Double.parseDouble(forecastData.get("WSD")),
                pty,
                pcpValue,
                Double.parseDouble(forecastData.get("POP")),
                snoValue,
                DEFAULT_TEMPERATURE,
                DEFAULT_TEMPERATURE
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