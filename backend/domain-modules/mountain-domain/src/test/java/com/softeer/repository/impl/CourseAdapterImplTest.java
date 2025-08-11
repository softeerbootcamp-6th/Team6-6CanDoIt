package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CourseFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseAdapterImplTest {

    @Mock
    private CourseJdbcRepository courseJdbcRepository;

    @InjectMocks
    private CourseAdapterImpl target;

    @Test
    @DisplayName("findCoursesByMountainId : mountainId로 Course 목록을 반환한다")
    void findCoursesByMountainId_success() {
        // given
        final long mountainId = 7L;

        final List<Course> expectedCourses = List.of(
                CourseFixture.builder().id(1L).name("코스 A").build(),
                CourseFixture.builder().id(2L).name("코스 B").build()
        );

        when(courseJdbcRepository.findCoursesByMountainId(mountainId))
                .thenReturn(expectedCourses);

        // when (실행)
        final List<Course> actualResult = target.findCoursesByMountainId(mountainId);

        // then (검증)
        assertThat(actualResult)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedCourses); // 객체의 참조가 동일한지 확인

        assertThat(actualResult.get(0).id()).isEqualTo(1L);
        assertThat(actualResult.get(0).name()).isEqualTo("코스 A");

        verify(courseJdbcRepository, times(1)).findCoursesByMountainId(mountainId);
        verifyNoMoreInteractions(courseJdbcRepository);
    }
}