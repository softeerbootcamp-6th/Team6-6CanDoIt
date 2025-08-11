package com.softeer.repository.impl;

import com.softeer.domain.*;
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
            
                mi.image_url AS mountain_image_url,
                ci.image_url AS course_image_url,
            
                mg.id AS mountain_grid_id,
                mg.x AS mountain_grid_x,
                mg.y AS mountain_grid_y,
            
                start_g.id AS start_grid_id,
                start_g.x AS start_grid_x,
                start_g.y AS start_grid_y,
            
                dest_g.id AS dest_grid_id,
                dest_g.x AS dest_grid_x,
                dest_g.y AS dest_grid_y
            
            FROM course c
            INNER JOIN mountain m ON m.id = c.mountain_id
            INNER JOIN sun_time s ON s.mountain_id = m.id 
                                   AND s.date = :date
            INNER JOIN image mi ON mi.id = m.image_id
            INNER JOIN image ci ON ci.id = c.image_id
            INNER JOIN grid mg ON mg.id = m.grid_id
            
            INNER JOIN course_point start_cp ON start_cp.id = (
                SELECT MIN(cp_min.id) FROM course_point cp_min WHERE cp_min.course_id = c.id
            )
            INNER JOIN grid start_g ON start_g.id = start_cp.grid_id
            
            INNER JOIN course_point dest_cp ON dest_cp.id = (
                SELECT MAX(cp_max.id) FROM course_point cp_max WHERE cp_max.course_id = c.id
            )
            INNER JOIN grid dest_g ON dest_g.id = dest_cp.grid_id
            
            WHERE c.id = :courseId
            """;

    public CoursePlan findCoursePlan(long courseId, LocalDate date) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("date", java.sql.Date.valueOf(date))
                .addValue("courseId", courseId);

        return jdbcTemplate.queryForObject(SELECT_COURSE_PLAN, params, ROW_MAPPER);
    }

    protected static final RowMapper<CoursePlan> ROW_MAPPER = (rs, rowNum) -> {
        var mountainGrid = new Grid(
                rs.getInt("mountain_grid_id"),
                rs.getInt("mountain_grid_x"),
                rs.getInt("mountain_grid_y")
        );

        var mountain = new Mountain(
                rs.getLong("mountain_id"),
                rs.getString("mountain_name"),
                rs.getInt("mountain_altitude"),
                rs.getString("mountain_image_url"),
                rs.getString("description"),
                mountainGrid
        );

        var sunTime = new SunTime(
                rs.getTime("sunrise").toLocalTime(),
                rs.getTime("sunset").toLocalTime()
        );

        var startGrid = new Grid(
                rs.getInt("start_grid_id"),
                rs.getInt("start_grid_x"),
                rs.getInt("start_grid_y")
        );

        var destinationGrid = new Grid(
                rs.getInt("dest_grid_id"),
                rs.getInt("dest_grid_x"),
                rs.getInt("dest_grid_y")
        );

        var course = new Course(
                rs.getLong("course_id"),
                rs.getString("course_name"),
                rs.getDouble("total_distance"),
                rs.getDouble("total_duration"),
                rs.getInt("course_altitude"),
                Level.valueOf(rs.getString("level")),
                rs.getString("course_image_url"),
                startGrid,
                destinationGrid
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