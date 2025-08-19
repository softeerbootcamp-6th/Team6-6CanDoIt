package com.softeer.batch.forecast.mountain.writer;

import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.batch.common.writersupporter.ForecastWriterSupporter;
import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractMountainForecastWriter implements ItemWriter<MountainDailyForecast> {

    protected final ForecastWriterSupporter forecastWriterSupporter;

    @Override
    public synchronized void write(Chunk<? extends MountainDailyForecast> chunk) {
        writeSunTime(chunk);
        writeForecast(chunk);
    }

    private void writeSunTime(Chunk<? extends MountainDailyForecast> chunk) {
        SqlParameterSource[] sunTimeParams = chunk.getItems().stream()
                .filter(item -> item.sunTime() != null && item.sunTime().sunrise() != null)
                .map(forecastWriterSupporter::createSunTimeParams)
                .toArray(SqlParameterSource[]::new);

        forecastWriterSupporter.batchUpdateSunTime(sunTimeParams);
    }

    private void writeForecast(Chunk<? extends MountainDailyForecast> chunk) {
        Map<Integer, List<Forecast>> forecastsByGridId = chunk.getItems().stream()
                .collect(Collectors.toMap(
                        MountainDailyForecast::gridId,
                        MountainDailyForecast::hourlyForecasts
                ));

        List<SqlParameterSource> paramsToInsert = new ArrayList<>();

        for (Map.Entry<Integer, List<Forecast>> entry : forecastsByGridId.entrySet()) {
            int gridId = entry.getKey();
            List<Forecast> forecasts = filterForecasts(entry.getValue());

            forecasts.stream()
                    .map(hourly -> forecastWriterSupporter.mapForecastToSqlParams(hourly, gridId))
                    .forEach(paramsToInsert::add);
        }

        if (!paramsToInsert.isEmpty()) {
            forecastWriterSupporter.batchUpdateForecast(paramsToInsert.toArray(new SqlParameterSource[0]));
        }
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);
}
