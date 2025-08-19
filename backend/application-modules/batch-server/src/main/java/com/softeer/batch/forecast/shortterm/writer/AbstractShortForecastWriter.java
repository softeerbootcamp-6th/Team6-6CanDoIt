package com.softeer.batch.forecast.shortterm.writer;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.forecast.shortterm.writersupporter.ShortForecastWriterSupporter;
import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractShortForecastWriter implements ItemWriter<ShortForecastList> {

    protected final ShortForecastWriterSupporter shortForecastWriterSupporter;


    @Override
    public synchronized void write(Chunk<? extends ShortForecastList> chunk) throws Exception {
        MapSqlParameterSource[] batch = chunk.getItems().stream()
                .flatMap(item -> {
                    List<Forecast> filtered = filterForecasts(item.forecasts());

                    return filtered.stream()
                            .map(f -> shortForecastWriterSupporter
                                    .mapForecastToSqlParams(f, item.gridId()));
                })
                .toArray(MapSqlParameterSource[]::new);

        if (batch.length > 0) {
            shortForecastWriterSupporter.batchUpdateForecast(batch);
        }
    }

    protected abstract List<Forecast> filterForecasts(List<Forecast> forecasts);

}
