package com.softeer.batch.common.writersupporter;

import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@StepScope
@RequiredArgsConstructor
public class SunTimeJdbcWriter {

    private static final String INSERT_SUN_TIME =
            "INSERT INTO sun_time (sunrise, sunset, date, mountain_id) " +
                    "VALUES (:sunrise, :sunset, :date, :mountainId) " +
                    "ON DUPLICATE KEY UPDATE sunrise = VALUES(sunrise), sunset = VALUES(sunset)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchUpdateSunTime(SqlParameterSource[] params) {
        if (params != null && params.length > 0) {
            namedParameterJdbcTemplate.batchUpdate(INSERT_SUN_TIME, params);
        }
    }

    public MapSqlParameterSource createSunTimeParams(MountainDailyForecast item) {
        return new MapSqlParameterSource()
                .addValue("sunrise", item.sunTime().sunrise())
                .addValue("sunset", item.sunTime().sunset())
                .addValue("date", LocalDate.now())
                .addValue("mountainId", item.mountainId());
    }
}
