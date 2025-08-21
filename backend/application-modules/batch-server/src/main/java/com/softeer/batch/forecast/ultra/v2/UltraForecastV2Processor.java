package com.softeer.batch.forecast.ultra.v2;

import com.softeer.batch.forecast.ultra.dto.UltraForecastResponse;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.batch.forecast.ultra.v1.UltraForecastV1JobConfig;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import com.softeer.shortterm.UltraForecastApiCaller;
import com.softeer.shortterm.dto.request.ShortForecastApiRequest;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import com.softeer.throttle.manager.AsyncRetryHandler;
import com.softeer.time.ApiTime;
import com.softeer.time.ApiTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static com.softeer.batch.forecast.ultra.v2.UltraForecastV2JobConfig.SCHEDULED_ULTRA_FORECAST_V2_EX_SERVICE;

@Slf4j
@Component
@StepScope
public class UltraForecastV2Processor implements ItemProcessor<Grid, CompletableFuture<UltraForecastResponseList>> {
    private final UltraForecastApiCaller kmaApiCaller;
    private final ForecastMapper forecastMapper;
    private final AsyncRetryHandler asyncRetryHandler;
    private final ExecutorService executorService;

    public UltraForecastV2Processor(UltraForecastApiCaller kmaApiCaller,
                                    ForecastMapper forecastMapper, AsyncRetryHandler asyncRetryHandler,
                                    @Qualifier(SCHEDULED_ULTRA_FORECAST_V2_EX_SERVICE) ExecutorService executorService) {
        this.kmaApiCaller = kmaApiCaller;
        this.forecastMapper = forecastMapper;
        this.asyncRetryHandler = asyncRetryHandler;
        this.executorService = executorService;
    }

    @Value("${kma.api.key.short}")
    private String serviceKey;

    @Value("#{jobParameters[dateTime]}")
    private LocalDateTime dateTime;


    @Override
    public CompletableFuture<UltraForecastResponseList> process(Grid grid) throws Exception {
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

        return asyncRetryHandler.submitAsync(
                UltraForecastV1JobConfig.ULTRA_FORECAST,
                () -> processApiResponse(grid, kmaApiCaller.call(request))
        , executorService);
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
