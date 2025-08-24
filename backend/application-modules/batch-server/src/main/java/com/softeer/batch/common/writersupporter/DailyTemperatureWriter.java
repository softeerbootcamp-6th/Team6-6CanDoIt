package com.softeer.batch.common.writersupporter;

import com.softeer.domain.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class DailyTemperatureWriter {

    private static final String INSERT_DAILY_TEMPERATURE =
            "INSERT INTO daily_temperature (date, highest_temperature, lowest_temperature, grid_id) " +
                    "VALUES (:date, :highestTemperature, :lowestTemperature, :gridId) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "highest_temperature = VALUES(highest_temperature), " +
                    "lowest_temperature = VALUES(lowest_temperature)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchUpdate(SqlParameterSource[] params) {
        if (params != null && params.length > 0) {
            namedParameterJdbcTemplate.batchUpdate(INSERT_DAILY_TEMPERATURE, params);
        }
    }

    public MapSqlParameterSource mapDailyTemperatureToSqlParams(LocalDate date, List<Forecast> forecasts, long gridId) {
        double highestTemperature = findHighestTemperature(forecasts);
        double lowestTemperature = findLowestTemperature(forecasts);

        return new MapSqlParameterSource()
                .addValue("date", date)
                .addValue("highestTemperature", highestTemperature)
                .addValue("lowestTemperature", lowestTemperature)
                .addValue("gridId", gridId);
    }

    private double findHighestTemperature(List<Forecast> forecasts) {
        return forecasts.stream()
                .map(forecast -> forecast.dailyTemperature().highestTemperature())
                .findFirst().get();
    }

    private double findLowestTemperature(List<Forecast> forecasts) {
        return forecasts.stream()
                .map(forecast -> forecast.dailyTemperature().lowestTemperature())
                .findFirst().get();
    }
}
