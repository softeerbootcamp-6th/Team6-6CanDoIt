package com.softeer.batch.forecast.ultra;

import com.softeer.batch.forecast.ultra.dto.UltraForecastResponse;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class UltraForecastJdbcWriter {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String UPDATE_FORECAST = """
    UPDATE forecast SET
        temperature = :temperature,
        precipitation = :precipitation,
        sky = :sky,
        humidity = :humidity,
        precipitation_type = :precipitationType,
        wind_dir = :windDirection,
        wind_speed = :windSpeed
    WHERE grid_id = :gridId AND type = 'SHORT' AND date_time = :dateTime
    """;

    public void batchUpdateForecast(List<? extends UltraForecastResponseList> items) {
        namedParameterJdbcTemplate.batchUpdate(UPDATE_FORECAST, toSqlParameterSource(items));
    }

    private MapSqlParameterSource[] toSqlParameterSource(List<? extends UltraForecastResponseList> items) {
        return items.stream()
                .flatMap(listItem ->
                        listItem.response().stream().map(this::createParameterSource)
                )
                .toArray(MapSqlParameterSource[]::new);
    }

    private MapSqlParameterSource createParameterSource(UltraForecastResponse ultraForecast){
        return new MapSqlParameterSource()
                .addValue("gridId", ultraForecast.gridId())
                .addValue("temperature", ultraForecast.temperature())
                .addValue("precipitation", ultraForecast.precipitation())
                .addValue("sky", ultraForecast.sky().name())
                .addValue("humidity", ultraForecast.humidity())
                .addValue("precipitationType", ultraForecast.precipitationType().name())
                .addValue("windDirection", ultraForecast.windDirection().name())
                .addValue("windSpeed", ultraForecast.windSpeed())
                .addValue("dateTime", ultraForecast.dateTime());
    }
}
