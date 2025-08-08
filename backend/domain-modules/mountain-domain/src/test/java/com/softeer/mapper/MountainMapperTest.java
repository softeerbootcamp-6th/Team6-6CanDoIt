package com.softeer.mapper;

import com.softeer.domain.Mountain;
import com.softeer.entity.ImageEntity;
import com.softeer.entity.MountainEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MountainMapperTest {

    private final MountainMapper target = Mappers.getMapper(MountainMapper.class);

    @Test
    @DisplayName("toDomain: MountainEntity → Mountain 변환이 필드를 올바로 매핑한다")
    void toDomain_success() {
        // given
        ImageEntity image = mock(ImageEntity.class);
        when(image.getImageUrl()).thenReturn("https://cdn/img1.jpg");

        MountainEntity entity = mock(MountainEntity.class);
        when(entity.getId()).thenReturn(1L);
        when(entity.getName()).thenReturn("설악산");
        when(entity.getAltitude()).thenReturn(1708);
        when(entity.getDescription()).thenReturn("한국의 대표 명산");
        when(entity.getImageEntity()).thenReturn(image);

        // when
        Mountain result = target.toDomain(entity);

        // then
        assertAll(
                () -> assertEquals(1L, result.id()),
                () -> assertEquals("설악산", result.name()),
                () -> assertEquals(1708, result.altitude()),
                () -> assertEquals("한국의 대표 명산", result.description()),
                () -> assertEquals("https://cdn/img1.jpg", result.imageUrl())
        );
    }

    @Test
    @DisplayName("toDomainList: List<MountainEntity> → List<Mountain> 변환이 단건 매핑을 반복 호출한다")
    void toDomainList_success() {
        // given
        ImageEntity img1 = mock(ImageEntity.class);
        when(img1.getImageUrl()).thenReturn("url1");
        MountainEntity e1 = mock(MountainEntity.class);
        when(e1.getId()).thenReturn(1L);
        when(e1.getName()).thenReturn("설악산");
        when(e1.getAltitude()).thenReturn(1708);
        when(e1.getDescription()).thenReturn("desc1");
        when(e1.getImageEntity()).thenReturn(img1);

        ImageEntity img2 = mock(ImageEntity.class);
        when(img2.getImageUrl()).thenReturn("url2");
        MountainEntity e2 = mock(MountainEntity.class);
        when(e2.getId()).thenReturn(2L);
        when(e2.getName()).thenReturn("지리산");
        when(e2.getAltitude()).thenReturn(1915);
        when(e2.getDescription()).thenReturn("desc2");
        when(e2.getImageEntity()).thenReturn(img2);

        // when
        List<Mountain> list = target.toDomainList(List.of(e1, e2));

        // then
        assertEquals(2, list.size());
        assertEquals("url1", list.get(0).imageUrl());
        assertEquals("url2", list.get(1).imageUrl());
    }
}