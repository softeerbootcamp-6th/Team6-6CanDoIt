package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.Mountain;
import com.softeer.domain.MountainFixture;
import com.softeer.entity.CourseEntity;
import com.softeer.entity.MountainEntity;
import com.softeer.error.CustomException;
import com.softeer.exception.MountainException;
import com.softeer.mapper.CourseMapper;
import com.softeer.mapper.MountainMapper;
import com.softeer.repository.MountainAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MountainAdapterImplTest {

    @Mock
    private MountainJpaRepository mountainJpaRepository;

    @Mock
    private MountainMapper mountainMapper;

    @InjectMocks
    private MountainAdapterImpl target;

    @Test
    @DisplayName("findAllMountains: 엔티티 → 도메인 리스트를 반환한다")
    void findAllMountains_success() {
        // given
        MountainEntity e1 = mock(MountainEntity.class);
        MountainEntity e2 = mock(MountainEntity.class);
        List<MountainEntity> entities = List.of(e1, e2);

        List<Mountain> expected = List.of(
                MountainFixture.builder()
                        .id(1L)
                        .name("설악산")
                        .altitude(1708)
                        .build(),
                MountainFixture.builder()
                        .id(2L)
                        .name("지리산")
                        .altitude(1915)
                        .build()
        );

        when(mountainJpaRepository.findAll()).thenReturn(entities);
        when(mountainMapper.toDomainList(entities)).thenReturn(expected);

        // When
        List<Mountain> result = target.findAllMountains();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("설악산");
        assertThat(result.get(0).altitude()).isEqualTo(1708);
        assertThat(result.get(1).name()).isEqualTo("지리산");
        assertThat(result.get(1).altitude()).isEqualTo(1915);

        verify(mountainJpaRepository).findAll();
        verify(mountainMapper).toDomainList(entities);
    }

    @Test
    @DisplayName("findMountainById: 엔티티가 존재하면 도메인으로 매핑해 반환한다")
    void findMountainById_success() {
        // given
        long id = 1L;
        MountainEntity entity = mock(MountainEntity.class);
        Mountain expected = mock(Mountain.class);

        when(mountainJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mountainMapper.toDomain(entity)).thenReturn(expected);

        // when
        Mountain result = target.findMountainById(id);

        // then
        assertSame(expected, result);
        verify(mountainJpaRepository).findById(id);
        verify(mountainMapper).toDomain(entity);
    }

    @Test
    @DisplayName("findMountainById: 엔티티가 없으면 MountainException NOT_FOUND")
    void findMountainById_notFound() {
        // given
        long id = 42L;
        when(mountainJpaRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        var e = assertThrows(CustomException.class,
                () -> target.findMountainById(id)
        );

        assertEquals(MountainException.NOT_FOUND.getErrorCode(), e.getErrorCode());
        verify(mountainJpaRepository).findById(id);
        verifyNoInteractions(mountainMapper);
    }
}