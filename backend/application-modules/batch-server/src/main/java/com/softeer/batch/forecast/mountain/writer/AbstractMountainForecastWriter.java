package com.softeer.batch.forecast.mountain.writer;

import com.softeer.batch.common.writersupporter.ForecastJdbcWriter;
import com.softeer.batch.common.writersupporter.SunTimeJdbcWriter;
import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
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

    protected final ForecastJdbcWriter forecastWriterSupporter;
    protected final SunTimeJdbcWriter sunTimeJdbcWriter;

    @Override
    public void write(Chunk<? extends MountainDailyForecast> chunk) {
        writeSunTime(chunk);
        writeForecast(chunk);
    }

    private void writeSunTime(Chunk<? extends MountainDailyForecast> chunk) {
        SqlParameterSource[] sunTimeParams = chunk.getItems().stream()
                .map(sunTimeJdbcWriter::mapSunTimeToSqlParams)
                .flatMap(List::stream)
                .toArray(SqlParameterSource[]::new);

        sunTimeJdbcWriter.batchUpdate(sunTimeParams);
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
            forecastWriterSupporter.batchUpdate(paramsToInsert.toArray(new SqlParameterSource[0]));
        }
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);
}
