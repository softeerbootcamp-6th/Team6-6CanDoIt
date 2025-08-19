package com.softeer.batch.forecast.shortterm.writer;

import com.softeer.batch.common.writersupporter.ForecastWriterSupporter;
import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractShortForecastWriter implements ItemWriter<ShortForecastList> {

    protected final ForecastWriterSupporter forecastWriterSupporter;


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

        if (batch.length > 0) {
            forecastWriterSupporter.batchUpdateForecast(batch);
        }
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);

}
