package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseJdbcRepositoryTest {

    @Test
    @DisplayName("ResultSet의 1행을 Course 도메인 객체로 정확히 변환한다")
    void mapRow_Success() throws SQLException {
        // given
        long courseId = 101L;
        String courseName = "한라산 영실 코스";
        double totalDistance = 5.8;
        double totalDuration = 150; // 분 단위
        Level level = Level.MEDIUM;
        String imageUrl = "https://cdn/course/image.jpg";

        int startGridId = 1;
        int startGridX = 10;
        int startGridY = 20;

        int destGridId = 5;
        int destGridX = 55;
        int destGridY = 65;

        ResultSet rs = mock(ResultSet.class);

        when(rs.getLong("course_id")).thenReturn(courseId);
        when(rs.getString("course_name")).thenReturn(courseName);
        when(rs.getDouble("total_distance")).thenReturn(totalDistance);
        when(rs.getDouble("total_duration")).thenReturn(totalDuration);
        when(rs.getString("level")).thenReturn(level.name());
        when(rs.getString("image_url")).thenReturn(imageUrl);

        when(rs.getInt("start_grid_id")).thenReturn(startGridId);
        when(rs.getInt("start_grid_x")).thenReturn(startGridX);
        when(rs.getInt("start_grid_y")).thenReturn(startGridY);

        when(rs.getInt("dest_grid_id")).thenReturn(destGridId);
        when(rs.getInt("dest_grid_x")).thenReturn(destGridX);
        when(rs.getInt("dest_grid_y")).thenReturn(destGridY);

        // when
        Course actualCourse = CourseJdbcRepository.ROW_MAPPER.mapRow(rs, 1);

        // then
        assertAll("course",
                () -> assertEquals(courseId, actualCourse.id()),
                () -> assertEquals(courseName, actualCourse.name()),
                () -> assertEquals(totalDistance, actualCourse.totalDistance()),
                () -> assertEquals(totalDuration, actualCourse.totalDuration()),
                () -> assertEquals(level, actualCourse.level()),
                () -> assertEquals(imageUrl, actualCourse.imageUrl())
        );

        Grid actualStartGrid = actualCourse.startGrid();
        assertAll("startGrid",
                () -> assertEquals(startGridId, actualStartGrid.id()),
                () -> assertEquals(startGridX, actualStartGrid.x()),
                () -> assertEquals(startGridY, actualStartGrid.y())
        );

        Grid actualDestinationGrid = actualCourse.destinationGrid();
        assertAll("destinationGrid",
                () -> assertEquals(destGridId, actualDestinationGrid.id()),
                () -> assertEquals(destGridX, actualDestinationGrid.x()),
                () -> assertEquals(destGridY, actualDestinationGrid.y())
        );
    }
}