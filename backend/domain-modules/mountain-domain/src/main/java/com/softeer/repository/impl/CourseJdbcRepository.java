package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_COURSES_BY_MOUNTAIN_ID = """
            WITH ranked_points AS (
                SELECT
                    id,
                    course_id,
                    grid_id,
                    ROW_NUMBER() OVER(PARTITION BY course_id ORDER BY id ASC) as start_rank,
                    ROW_NUMBER() OVER(PARTITION BY course_id ORDER BY id DESC) as end_rank
                FROM course_point
            ),
            start_points AS (
                SELECT course_id, grid_id FROM ranked_points WHERE start_rank = 1
            ),
            end_points AS (
                SELECT course_id, grid_id FROM ranked_points WHERE end_rank = 1
            )
            SELECT
                c.id AS course_id,
                c.name AS course_name,
                c.total_distance,
                c.total_duration,
                c.altitude AS course_altitude,
                c.level,
                i.image_url,
                start_g.id AS start_grid_id,
                start_g.x AS start_grid_x,
                start_g.y AS start_grid_y,
                end_g.id AS dest_grid_id,
                end_g.x AS dest_grid_x,
                end_g.y AS dest_grid_y
            FROM course c
            INNER JOIN image i ON i.id = c.image_id
            INNER JOIN start_points sp ON c.id = sp.course_id
            INNER JOIN grid start_g ON start_g.id = sp.grid_id
            INNER JOIN end_points ep ON c.id = ep.course_id
            INNER JOIN grid end_g ON end_g.id = ep.grid_id
            WHERE c.mountain_id = :mountainId;
            """;

    public List<Course> findCoursesByMountainId(long mountainId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("mountainId", mountainId);

        return jdbcTemplate.query(SELECT_COURSES_BY_MOUNTAIN_ID, params, ROW_MAPPER);
    }

    protected static final RowMapper<Course> ROW_MAPPER = (rs, rowNum) -> {
        Grid startGrid = new Grid(
            rs.getInt("start_grid_id"),
            rs.getInt("start_grid_x"),
            rs.getInt("start_grid_y")
        );

        Grid destinationGrid = new Grid(
            rs.getInt("dest_grid_id"),
            rs.getInt("dest_grid_x"),
            rs.getInt("dest_grid_y")
        );

        return new Course(
            rs.getLong("course_id"),
            rs.getString("course_name"),
            rs.getDouble("total_distance"),
            rs.getDouble("total_duration"),
            rs.getInt("course_altitude"),
            Level.valueOf(rs.getString("level")),
            rs.getString("image_url"),
            startGrid,
            destinationGrid
        );
    };
}
