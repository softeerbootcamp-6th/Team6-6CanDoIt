package com.softeer.domain;

import com.softeer.error.CustomException;
import com.softeer.exception.CoursePlanException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CoursePlanTest {

    @Test
    @DisplayName("CoursePlan 생성 – 정상 입력이면 예외 없이 필드가 그대로 보존된다")
    void createCoursePlan_success() {
        // given
        Course   course   = CourseFixture.createDefault();
        Mountain mountain = MountainFixture.createDefault();
        SunTime  sunTime  = new SunTime(LocalTime.of(5, 30), LocalTime.of(19, 30));
        LocalDate date    = LocalDate.of(2025, 8, 15);

        // when
        CoursePlan plan = new CoursePlan(course, mountain, sunTime, date);

        // then
        assertAll(
                () -> assertEquals(course,   plan.course()),
                () -> assertEquals(mountain, plan.mountain()),
                () -> assertEquals(sunTime,  plan.sunTime()),
                () -> assertEquals(date,     plan.date())
        );
    }

    @Nested
    @DisplayName("널 파라미터 검증")
    class NullParameter {
        // given
        Course   course   = CourseFixture.createDefault();
        Mountain mountain = MountainFixture.createDefault();
        SunTime  sunTime  = new SunTime(LocalTime.of(6, 0), LocalTime.of(18, 0));
        LocalDate date    = LocalDate.of(2025, 8, 15);

        @Test
        @DisplayName("course 가 null이면 CustomException")
        void nullCourse() {
            // when, then
            CustomException e = assertThrows(CustomException.class,
                    () -> new CoursePlan(null, mountain, sunTime, date));
            assertEquals(CoursePlanException.COURSE_PLAN_INTERNAL_ERROR.getErrorCode(), e.getErrorCode());
        }

        @Test
        @DisplayName("mountain 가 null이면 CustomException")
        void nullMountain() {
            // when, then
            CustomException e = assertThrows(CustomException.class,
                    () -> new CoursePlan(course, null, sunTime, date));
            assertEquals(CoursePlanException.COURSE_PLAN_INTERNAL_ERROR.getErrorCode(), e.getErrorCode());
        }

        @Test
        @DisplayName("sunTime 이 null이면 CustomException")
        void nullSunTime() {
            // when, then
            CustomException e = assertThrows(CustomException.class,
                    () -> new CoursePlan(course, mountain, null, date));
            assertEquals(CoursePlanException.COURSE_PLAN_INTERNAL_ERROR.getErrorCode(), e.getErrorCode());
        }

        @Test
        @DisplayName("date 가 null이면 CustomException")
        void nullDate() {
            // when, then
            CustomException e = assertThrows(CustomException.class,
                    () -> new CoursePlan(course, mountain, sunTime, null));
            assertEquals(CoursePlanException.COURSE_PLAN_INTERNAL_ERROR.getErrorCode(), e.getErrorCode());
        }
    }
}