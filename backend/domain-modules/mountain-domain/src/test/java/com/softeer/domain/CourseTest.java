package com.softeer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CourseTest {

    @Test
    @DisplayName("Course 생성 - 정상 입력이면 예외가 발생하지 않는다")
    void createCourse_success() {
        // given, when, then
        assertDoesNotThrow(CourseFixture::createDefault);
    }
}