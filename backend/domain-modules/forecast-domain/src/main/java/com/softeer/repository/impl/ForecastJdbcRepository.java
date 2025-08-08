package com.softeer.repository.impl;

import com.softeer.domain.Forecast;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ForecastJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_FORECAST = """
            (
                SELECT
                    f.id, f.temperature, f.type, f.humidity,
                    f.precipitation, f.sky, f.precipitation_type,
                    f.wind_dir, f.wind_speed, f.date_time,
                    sfd.precipitation_probability, sfd.snow_accumulation,
                    sfd.highest_temperature, sfd.lowest_temperature
                FROM forecast f
                INNER JOIN forecast f_short ON (
                    f_short.grid_id = f.grid_id
                    AND f_short.date_time = f.date_time
                    AND f_short.type = 'SHORT'
                )
                LEFT JOIN short_forecast_detail sfd ON f_short.id = sfd.forecast_id
                WHERE f.type = 'ULTRA'
                  AND f.grid_id = ?
                  AND f.date_time BETWEEN ? AND ?
            )
            UNION ALL
            (
                -- 해당 시간에 ULTRA 예보가 없는 SHORT 예보
                SELECT
                    f.id, f.temperature, f.type, f.humidity,
                    f.precipitation, f.sky, f.precipitation_type,
                    f.wind_dir, f.wind_speed, f.date_time,
                    sfd.precipitation_probability, sfd.snow_accumulation,
                    sfd.highest_temperature, sfd.lowest_temperature
                FROM forecast f
                LEFT JOIN short_forecast_detail sfd ON f.id = sfd.forecast_id
                WHERE f.type = 'SHORT'
                  AND f.grid_id = ?
                  AND f.date_time BETWEEN ? AND ?
                  AND NOT EXISTS (
                      SELECT 1 FROM forecast ultra_f
                      WHERE ultra_f.grid_id = f.grid_id
                        AND ultra_f.date_time = f.date_time
                        AND ultra_f.type = 'ULTRA'
                  )
            )
            ORDER BY date_time ASC;
            """;

    public List<Forecast> findForecastsFor24Hours(long gridId, LocalDateTime startTime) {

        LocalDateTime endTime = startTime.plusHours(23);

        return jdbcTemplate.query(SELECT_FORECAST, new ForecastRowMapper(),
                gridId, startTime, endTime,
                gridId, startTime, endTime);
    }

    private static class ForecastRowMapper implements RowMapper<Forecast> {

        @Override
        public Forecast mapRow(final ResultSet rs, final int rowNum) throws SQLException {

            long id = rs.getLong("id");
            LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
            ForecastType forecastType = ForecastType.valueOf(rs.getString("type"));
            Sky sky = Sky.valueOf(rs.getString("sky"));
            double temperature = rs.getDouble("temperature");
            double humidity = rs.getDouble("humidity");
            WindDirection windDir = WindDirection.valueOf(rs.getString("wind_dir"));
            double windSpeed = rs.getDouble("wind_speed");
            PrecipitationType precipitationType = PrecipitationType.valueOf(rs.getString("precipitation_type"));
            String precipitation = rs.getString("precipitation");
            double precipitationProbability = rs.getDouble("precipitation_probability");
            double snowAccumulation = rs.getDouble("snow_accumulation");
            int highestTemperature = rs.getInt("highest_temperature");
            int lowestTemperature = rs.getInt("lowest_temperature");

            return new Forecast(
                    id, dateTime, forecastType,
                    sky, temperature, humidity,
                    windDir, windSpeed, precipitationType,
                    precipitation, precipitationProbability,
                    snowAccumulation, highestTemperature, lowestTemperature);
        }
    }
}