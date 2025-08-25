package com.softeer.batch.forecast.shortterm.writer.v2;

import com.softeer.batch.common.writersupporter.DailyTemperatureWriter;
import com.softeer.batch.common.writersupporter.ForecastJdbcWriter;
import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.forecast.shortterm.redis.ShortForecastRedisWriter;
import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractShortForecastV2Writer implements ItemWriter<CompletableFuture<ShortForecastList>> {

    protected final ForecastJdbcWriter forecastWriterSupporter;
    protected final DailyTemperatureWriter dailyTemperatureWriterSupporter;
    protected final ShortForecastRedisWriter shortForecastRedisWriter;

    @Override
    public void write(Chunk<? extends CompletableFuture<ShortForecastList>> chunk) throws Exception {

        List<ShortForecastList> results = chunk.getItems().stream()
                .map(CompletableFuture::join)
                .toList();

        MapSqlParameterSource[] batch = results.stream()
                .flatMap(item -> {
                    List<Forecast> filtered = filterForecasts(item.forecasts());

                    return filtered.stream()
                            .map(forecast -> forecastWriterSupporter
                                    .mapForecastToSqlParams(forecast, item.gridId()));
                })
                .toArray(MapSqlParameterSource[]::new);

        MapSqlParameterSource[] dailyTemperatureBatch = results.stream()
                .flatMap(item ->
                        item.forecasts().stream()
                                .collect(Collectors.groupingBy(f -> f.dateTime().toLocalDate()))
                                .entrySet().stream()
                                .map(entry -> {
                                    LocalDate date = entry.getKey();
                                    List<Forecast> forecasts = entry.getValue();

                                    return dailyTemperatureWriterSupporter.mapDailyTemperatureToSqlParams(date, forecasts, item.gridId());
                                })
                ).toArray(MapSqlParameterSource[]::new);

        if (batch.length > 0) {
            forecastWriterSupporter.batchUpdate(batch);
        }

        if (dailyTemperatureBatch.length > 0) {
            dailyTemperatureWriterSupporter.batchUpdate(dailyTemperatureBatch);
        }

        shortForecastRedisWriter.pipelineUpdateShortForecast(results);
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);

}
