package com.softeer.repository.impl;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.domain.CoursePlan;
import com.softeer.entity.enums.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTestWithContainer
@SqlGroup({
        @Sql(scripts = {"/sql/course-plan-schema.sql", "/sql/course-plan-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CoursePlanJdbcRepositoryIntegrationTest {

    @Autowired
    CoursePlanJdbcRepository repository;

    @Test
    @DisplayName("코스-ID & 날짜로 CoursePlan 조회")
    void findCoursePlan_success() {
        // given
        long courseId = 1L;
        LocalDate date = LocalDate.of(2025, 8, 15);

        // when
        CoursePlan coursePlan = repository.findCoursePlan(courseId, date);

        // then
        assertThat(coursePlan.course().id()).isEqualTo(courseId);
        assertThat(coursePlan.date()).isEqualTo(date);
        assertThat(coursePlan.course().level()).isEqualTo(Level.HARD);
        assertThat(coursePlan.course().imageUrl()).isEqualTo("https://cdn.example.com/img/course_dinosaur.jpg");
        assertThat(coursePlan.mountain().name()).isEqualTo("설악산");
        assertThat(coursePlan.mountain().imageUrl()).isEqualTo("https://cdn.example.com/img/mountain_seorak.jpg");
        assertThat(coursePlan.sunTime().sunrise()).hasToString("05:30");
        assertThat(coursePlan.sunTime().sunset()).hasToString("19:30");
    }
}
