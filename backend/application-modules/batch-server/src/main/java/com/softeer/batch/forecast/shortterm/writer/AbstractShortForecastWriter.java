package com.softeer.batch.forecast.shortterm.writer;

import com.softeer.batch.common.writersupporter.ForecastJdbcWriter;
import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractShortForecastWriter implements ItemWriter<ShortForecastList> {

    protected final ForecastJdbcWriter forecastWriterSupporter;


    @Override
    public void write(Chunk<? extends ShortForecastList> chunk) throws Exception {
        MapSqlParameterSource[] batch = chunk.getItems().stream()
                .flatMap(item -> {
                    List<Forecast> filtered = filterForecasts(item.forecasts());

                    return filtered.stream()
                            .map(f -> forecastWriterSupporter
                                    .mapForecastToSqlParams(f, item.gridId()));
                })
                .toArray(MapSqlParameterSource[]::new);


        MapSqlParameterSource[] dailyTemperatureBatch = chunk.getItems().stream()
                .flatMap(forecastList ->
                        forecastList.forecasts().stream()
                                .collect(Collectors.groupingBy(f -> f.dateTime().toLocalDate()))
                                .entrySet().stream()
                                .map(entry -> {
                                    LocalDate date = entry.getKey();
                                    List<Forecast> forecasts = entry.getValue();
                                    double highest = forecasts.stream()
                                            .map(forecast -> forecast.dailyTemperature().highestTemperature())
                                            .findFirst().get();
                                    double lowest = forecasts.stream()
                                            .map(forecast -> forecast.dailyTemperature().lowestTemperature())
                                            .findFirst().get();

                                    return new MapSqlParameterSource()
                                            .addValue("date", date)
                                            .addValue("highestTemperature", highest)
                                            .addValue("lowestTemperature", lowest)
                                            .addValue("gridId", forecastList.gridId());
                                })

                ).toArray(MapSqlParameterSource[]::new);

        if (batch.length > 0) {
            forecastWriterSupporter.batchUpdateForecast(batch);
        }

        if (dailyTemperatureBatch.length > 0) {
            forecastWriterSupporter.batchUpdateDailyTemperature(dailyTemperatureBatch);
        }
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);

}
