package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.entity.CourseEntity;
import com.softeer.mapper.CourseMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseAdapterImplTest {

    @Mock
    private CourseJpaRepository courseJpaRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseAdapterImpl target;

    @Test
    @DisplayName("findCoursesByMountainId : mountainId로 Course 목록을 반환한다")
    void findCoursesByMountainId_success() {
        // given
        long mountainId = 7L;
        var entityList = List.of(
                mock(CourseEntity.class), mock(CourseEntity.class));
        List<Course> expected = List.of(
                mock(Course.class), mock(Course.class));

        when(courseJpaRepository.findEntitiesByMountainId(mountainId))
                .thenReturn(entityList);
        when(courseMapper.toDomainList(entityList))
                .thenReturn(expected);

        // when
        List<Course> result = target.findCoursesByMountainId(mountainId);

        // then
        assertEquals(expected, result);
        verify(courseJpaRepository).findEntitiesByMountainId(mountainId);
        verify(courseMapper).toDomainList(entityList);
        verifyNoMoreInteractions(courseJpaRepository, courseMapper);
    }
}