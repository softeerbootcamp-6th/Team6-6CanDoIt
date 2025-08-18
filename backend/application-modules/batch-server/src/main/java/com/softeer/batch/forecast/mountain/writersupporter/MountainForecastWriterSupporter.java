package com.softeer.batch.forecast.mountain.writersupporter;

import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.domain.Forecast;
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
public class MountainForecastWriterSupporter {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_SUN_TIME =
            "INSERT INTO sun_time (sunrise, sunset, date, mountain_id) " +
                    "VALUES (:sunrise, :sunset, :date, :mountainId) " +
                    "ON DUPLICATE KEY UPDATE sunrise = VALUES(sunrise), sunset = VALUES(sunset)";

    private static final String INSERT_FORECAST =
            "INSERT INTO forecast (temperature, precipitation, sky, humidity, precipitation_type, wind_dir, wind_speed, date_time, type, precipitation_probability, snow_accumulation, grid_id) " +
                    "VALUES (:temperature, :precipitation, :sky, :humidity, :precipitationType, :windDir, :windSpeed, :dateTime, :type, :precipitationProbability, :snowAccumulation, :gridId) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "temperature = VALUES(temperature), precipitation = VALUES(precipitation), sky = VALUES(sky), humidity = VALUES(humidity), precipitation_type = VALUES(precipitation_type), " +
                    "wind_dir = VALUES(wind_dir), wind_speed = VALUES(wind_speed), type = VALUES(type), precipitation_probability = VALUES(precipitation_probability), snow_accumulation = VALUES(snow_accumulation)";

    public void batchUpdateSunTime(SqlParameterSource[] params) {
        if (params != null && params.length > 0) {
            namedParameterJdbcTemplate.batchUpdate(INSERT_SUN_TIME, params);
        }
    }

    public void batchUpdateForecast(SqlParameterSource[] params) {
        if (params != null && params.length > 0) {
            namedParameterJdbcTemplate.batchUpdate(INSERT_FORECAST, params);
        }
    }

    public MapSqlParameterSource createSunTimeParams(MountainDailyForecast item) {
        return new MapSqlParameterSource()
                        .addValue("sunrise", item.sunTime().sunrise())
                        .addValue("sunset", item.sunTime().sunset())
                        .addValue("date", LocalDate.now())
                        .addValue("mountainId", item.mountainId());
    }

    public MapSqlParameterSource mapForecastToSqlParams(Forecast hourly, Long gridId) {
        return new MapSqlParameterSource()
                .addValue("temperature", hourly.temperatureCondition().temperature())
                .addValue("precipitation", hourly.precipitationCondition().precipitation())
                .addValue("sky", hourly.skyCondition().sky().name())
                .addValue("humidity", hourly.humidityCondition().humidity())
                .addValue("precipitationType", hourly.precipitationCondition().precipitationType().name())
                .addValue("windDir", hourly.windCondition().direction().name())
                .addValue("windSpeed", hourly.windCondition().windSpeed())
                .addValue("dateTime", hourly.dateTime())
                .addValue("type", hourly.forecastType().name())
                .addValue("precipitationProbability", hourly.precipitationCondition().precipitationProbability())
                .addValue("snowAccumulation", hourly.precipitationCondition().snowAccumulation())
                .addValue("gridId", gridId);
    }
}
