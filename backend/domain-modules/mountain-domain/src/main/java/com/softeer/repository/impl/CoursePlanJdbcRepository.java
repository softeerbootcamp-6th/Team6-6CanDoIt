package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;
import com.softeer.domain.SunTime;
import com.softeer.entity.enums.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class CoursePlanJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_COURSE_PLAN = """
            SELECT
                c.id AS course_id,
                c.total_distance,
                c.altitude AS course_altitude,
                c.name AS course_name,
                c.level,
                c.total_duration,
            
                m.id AS mountain_id,
                m.name AS mountain_name,
                m.altitude AS mountain_altitude,
                m.description,
            
                s.sunrise,
                s.sunset,
                s.date,
            
                i.image_url
            FROM course c
            INNER JOIN mountain m ON m.id = c.mountain_id
            INNER JOIN sun_time s ON s.mountain_id = m.id 
                                   AND s.date = :date
            INNER JOIN image i ON i.id = m.image_id
            WHERE c.id = :courseId
            """;

    public CoursePlan findCoursePlan(long courseId, LocalDate date) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("date", java.sql.Date.valueOf(date))
                .addValue("courseId", courseId);

        return jdbcTemplate.queryForObject(SELECT_COURSE_PLAN, params, ROW_MAPPER);
    }

    protected static final RowMapper<CoursePlan> ROW_MAPPER = (rs, rowNum) -> {
        var mountain = new Mountain(
                rs.getLong("mountain_id"),
                rs.getString("mountain_name"),
                rs.getInt("mountain_altitude"),
                rs.getString("image_url"),
                rs.getString("description")
        );

        var sunTime = new SunTime(
                rs.getTime("sunrise").toLocalTime(),
                rs.getTime("sunset").toLocalTime()
        );

        var course = new Course(
                rs.getLong("course_id"),
                rs.getString("course_name"),
                rs.getDouble("total_distance"),
                rs.getInt("total_duration"),
                Level.valueOf(rs.getString("level"))
        );

        var date = rs.getDate("date").toLocalDate();

        return new CoursePlan(
                course,
                mountain,
                sunTime,
                date
        );
    };
}
