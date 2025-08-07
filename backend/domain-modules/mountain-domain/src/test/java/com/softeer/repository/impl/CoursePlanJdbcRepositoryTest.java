package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;
import com.softeer.domain.SunTime;
import com.softeer.entity.enums.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.softeer.repository.impl.CoursePlanJdbcRepository.ROW_MAPPER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoursePlanJdbcRepositoryTest {

    @Test
    @DisplayName("ResultSet 1행을 CoursePlan 도메인으로 정확히 변환한다")
    void mapRow_success() throws Exception {
        // given
        long courseId = 10L;
        double distance = 12.3;
        int duration = 480;
        String courseName = "공룡능선";
        Level level = Level.HARD;

        long mountainId = 7L;
        String mountainName = "설악산";
        int mountainAlt = 1708;
        String imageUrl = "https://cdn/img.jpg";
        String description = "한국의 대표 명산";

        LocalTime sunrise = LocalTime.of(5, 30);
        LocalTime sunset = LocalTime.of(19, 30);
        LocalDate planDate = LocalDate.of(2025, 8, 15);

        ResultSet rs = mock(ResultSet.class);

        // course
        when(rs.getLong("course_id")).thenReturn(courseId);
        when(rs.getString("course_name")).thenReturn(courseName);
        when(rs.getDouble("total_distance")).thenReturn(distance);
        when(rs.getInt("total_duration")).thenReturn(duration);
        when(rs.getString("level")).thenReturn(level.name());

        // mountain
        when(rs.getLong("mountain_id")).thenReturn(mountainId);
        when(rs.getString("mountain_name")).thenReturn(mountainName);
        when(rs.getInt("mountain_altitude")).thenReturn(mountainAlt);
        when(rs.getString("image_url")).thenReturn(imageUrl);
        when(rs.getString("description")).thenReturn(description);

        // sun-time
        when(rs.getTime("sunrise")).thenReturn(Time.valueOf(sunrise));
        when(rs.getTime("sunset")).thenReturn(Time.valueOf(sunset));

        // date
        when(rs.getDate("date")).thenReturn(Date.valueOf(planDate));

        // when
        CoursePlan actual = ROW_MAPPER.mapRow(rs, 0);

        // then
        Mountain m = actual.mountain();
        assertAll("mountain",
                () -> assertEquals(mountainId, m.id()),
                () -> assertEquals(mountainName, m.name()),
                () -> assertEquals(mountainAlt, m.altitude()),
                () -> assertEquals(imageUrl, m.imageUrl()),
                () -> assertEquals(description, m.description())
        );

        SunTime st = actual.sunTime();
        assertAll("sunTime",
                () -> assertEquals(sunrise, st.sunrise()),
                () -> assertEquals(sunset, st.sunset())
        );

        Course c = actual.course();
        assertAll("course",
                () -> assertEquals(courseId, c.id()),
                () -> assertEquals(courseName, c.name()),
                () -> assertEquals(distance, c.totalDistance()),
                () -> assertEquals(duration, c.totalDuration()),
                () -> assertEquals(level, c.level())
        );

        assertEquals(planDate, actual.date());
    }


}