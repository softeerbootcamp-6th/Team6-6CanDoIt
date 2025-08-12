package com.softeer.repository.impl;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.domain.Course;
import com.softeer.entity.enums.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTestWithContainer
@SqlGroup({
        @Sql(scripts = {"/sql/course-plan-schema.sql", "/sql/course-plan-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CourseJdbcRepositoryIntegrationTest {

    @Autowired
    private CourseJdbcRepository repository;

    @Test
    @DisplayName("산 ID로 코스 목록을 조회 시, 각 코스의 시작/도착점 Grid 정보를 포함하여 정상적으로 반환한다")
    void findCoursesByMountainId_success() {
        // given
        long mountainId = 7L;

        // when
        List<Course> courses = repository.findCoursesByMountainId(mountainId);

        // then
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(2);

        Course dinosaurCourse = courses.stream()
                .filter(c -> c.id() == 1L)
                .findFirst()
                .orElseThrow();

        assertThat(dinosaurCourse.name()).isEqualTo("공룡능선");
        assertThat(dinosaurCourse.level()).isEqualTo(Level.HARD);
        assertThat(dinosaurCourse.imageUrl()).isEqualTo("https://cdn.example.com/img/course_dinosaur.jpg");

        assertThat(dinosaurCourse.startGrid().id()).isEqualTo(1);
        assertThat(dinosaurCourse.startGrid().x()).isEqualTo(60);
        assertThat(dinosaurCourse.startGrid().y()).isEqualTo(120);
        assertThat(dinosaurCourse.destinationGrid().id()).isEqualTo(1);
        assertThat(dinosaurCourse.destinationGrid().x()).isEqualTo(60);
        assertThat(dinosaurCourse.destinationGrid().y()).isEqualTo(120);

        Course biseondaeCourse = courses.stream()
                .filter(c -> c.id() == 2L)
                .findFirst()
                .orElseThrow();

        assertThat(biseondaeCourse.name()).isEqualTo("비선대 코스");
        assertThat(biseondaeCourse.level()).isEqualTo(Level.MEDIUM);
        assertThat(biseondaeCourse.imageUrl()).isEqualTo("https://cdn.example.com/img/course_bisandae.jpg");

        assertThat(biseondaeCourse.startGrid().id()).isEqualTo(1);
        assertThat(biseondaeCourse.startGrid().x()).isEqualTo(60);
        assertThat(biseondaeCourse.startGrid().y()).isEqualTo(120);
        assertThat(biseondaeCourse.destinationGrid().id()).isEqualTo(2);
        assertThat(biseondaeCourse.destinationGrid().x()).isEqualTo(61);
        assertThat(biseondaeCourse.destinationGrid().y()).isEqualTo(121);
    }

    @Test
    @DisplayName("코스가 없는 산 ID로 조회 시, 빈 리스트를 반환한다")
    void findCoursesByMountainId_whenNotFound_returnsEmptyList() {
        // given
        long nonExistentMountainId = 99L;

        // when
        List<Course> courses = repository.findCoursesByMountainId(nonExistentMountainId);

        // then
        assertThat(courses).isNotNull();
        assertThat(courses).isEmpty();
    }
}
