package com.softeer.batch.forecast.mountain.writer.v2;

import com.softeer.batch.common.writersupporter.ForecastJdbcWriter;
import com.softeer.batch.common.writersupporter.SunTimeJdbcWriter;
import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.batch.forecast.mountain.redis.MountainForecastRedisWriter;
import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractMountainForecastV2Writer implements ItemWriter<CompletableFuture<MountainDailyForecast>> {

    protected final ForecastJdbcWriter forecastWriterSupporter;
    protected final SunTimeJdbcWriter sunTimeJdbcWriter;
    protected final MountainForecastRedisWriter mountainForecastRedisWriter;

    @Override
    public void write(Chunk<? extends CompletableFuture<MountainDailyForecast>> chunk) {
        List<MountainDailyForecast> result = chunk.getItems().stream()
                .map(CompletableFuture::join)
                .toList();

        writeSunTime(result);
        writeForecast(result);
    }

    private void writeSunTime(List<MountainDailyForecast> result) {
        SqlParameterSource[] sunTimeParams = result.stream()
                .map(sunTimeJdbcWriter::mapSunTimeToSqlParams)
                .flatMap(List::stream)
                .toArray(SqlParameterSource[]::new);

        sunTimeJdbcWriter.batchUpdate(sunTimeParams);
    }

    private void writeForecast(List<MountainDailyForecast> result) {
        Map<Integer, List<Forecast>> forecastsByGridId = result.stream()
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

        mountainForecastRedisWriter.pipelineUpdateMountainForecast(result);
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);
}
