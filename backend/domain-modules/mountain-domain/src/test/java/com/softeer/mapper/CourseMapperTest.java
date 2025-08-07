package com.softeer.mapper;

import com.softeer.domain.Course;
import com.softeer.entity.CourseEntity;
import com.softeer.entity.enums.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseMapperTest {

    private final CourseMapper target = Mappers.getMapper(CourseMapper.class);

    @Test
    @DisplayName("toDomainList: List<CourseEntity> → List<Course> 매핑이 필드를 정확히 복사한다")
    void toDomainList_success() {
        // given
        CourseEntity e1 = mock(CourseEntity.class, RETURNS_DEEP_STUBS);
        when(e1.getId()).thenReturn(1L);
        when(e1.getName()).thenReturn("공룡능선");
        when(e1.getTotalDistance()).thenReturn(12.3);
        when(e1.getTotalDuration()).thenReturn(480);
        when(e1.getLevel()).thenReturn(Level.HARD);

        CourseEntity e2 = mock(CourseEntity.class, RETURNS_DEEP_STUBS);
        when(e2.getId()).thenReturn(2L);
        when(e2.getName()).thenReturn("비선대 코스");
        when(e2.getTotalDistance()).thenReturn(8.2);
        when(e2.getTotalDuration()).thenReturn(300);
        when(e2.getLevel()).thenReturn(Level.MEDIUM);

        // when
        List<Course> list = target.toDomainList(List.of(e1, e2));

        // then
        assertEquals(2, list.size());

        Course c1 = list.get(0);
        assertAll("첫 번째 코스",
                () -> assertEquals(1L, c1.id()),
                () -> assertEquals("공룡능선", c1.name()),
                () -> assertEquals(12.3, c1.totalDistance(), 1e-9),
                () -> assertEquals(480, c1.totalDuration()),
                () -> assertEquals(Level.HARD, c1.level())
        );

        Course c2 = list.get(1);
        assertAll("두 번째 코스",
                () -> assertEquals(2L, c2.id()),
                () -> assertEquals("비선대 코스", c2.name()),
                () -> assertEquals(8.2, c2.totalDistance(), 1e-9),
                () -> assertEquals(300, c2.totalDuration()),
                () -> assertEquals(Level.MEDIUM, c2.level())
        );
    }
}