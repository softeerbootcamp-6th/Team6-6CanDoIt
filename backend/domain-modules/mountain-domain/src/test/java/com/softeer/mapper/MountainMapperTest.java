package com.softeer.mapper;

import com.softeer.domain.Mountain;
import com.softeer.entity.GridEntity;
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

        GridEntity grid = mock(GridEntity.class);
        when(grid.getId()).thenReturn(1);
        when(grid.getX()).thenReturn(100);
        when(grid.getY()).thenReturn(200);

        MountainEntity entity = mock(MountainEntity.class);
        when(entity.getId()).thenReturn(1L);
        when(entity.getName()).thenReturn("설악산");
        when(entity.getAltitude()).thenReturn(1708);
        when(entity.getDescription()).thenReturn("한국의 대표 명산");
        when(entity.getImageEntity()).thenReturn(image);
        when(entity.getGridEntity()).thenReturn(grid);

        // when
        Mountain result = target.toDomain(entity);

        // then
        assertAll(
                () -> assertEquals(1L, result.id()),
                () -> assertEquals("설악산", result.name()),
                () -> assertEquals(1708, result.altitude()),
                () -> assertEquals("한국의 대표 명산", result.description()),

                () -> assertEquals("https://cdn/img1.jpg", result.imageUrl()),

                () -> assertEquals(1, result.grid().id()),
                () -> assertEquals(100, result.grid().x()),
                () -> assertEquals(200, result.grid().y())
        );
    }

    @Test
    @DisplayName("toDomainList: List<MountainEntity> → List<Mountain> 변환이 단건 매핑을 반복 호출한다")
    void toDomainList_success() {
        // given
        ImageEntity img1 = mock(ImageEntity.class);
        when(img1.getImageUrl()).thenReturn("url1");

        GridEntity grid1 = mock(GridEntity.class);
        when(grid1.getId()).thenReturn(1);
        when(grid1.getX()).thenReturn(100);
        when(grid1.getY()).thenReturn(200);

        MountainEntity e1 = mock(MountainEntity.class);
        when(e1.getId()).thenReturn(1L);
        when(e1.getName()).thenReturn("설악산");
        when(e1.getAltitude()).thenReturn(1708);
        when(e1.getDescription()).thenReturn("desc1");
        when(e1.getImageEntity()).thenReturn(img1);
        when(e1.getGridEntity()).thenReturn(grid1);

        ImageEntity img2 = mock(ImageEntity.class);
        when(img2.getImageUrl()).thenReturn("url2");

        GridEntity grid2 = mock(GridEntity.class);
        when(grid2.getId()).thenReturn(1);
        when(grid2.getX()).thenReturn(100);
        when(grid2.getY()).thenReturn(200);

        MountainEntity e2 = mock(MountainEntity.class);
        when(e2.getId()).thenReturn(2L);
        when(e2.getName()).thenReturn("지리산");
        when(e2.getAltitude()).thenReturn(1915);
        when(e2.getDescription()).thenReturn("desc2");
        when(e2.getImageEntity()).thenReturn(img2);
        when(e2.getGridEntity()).thenReturn(grid2);

        // when
        List<Mountain> list = target.toDomainList(List.of(e1, e2));

        // then
        assertAll(
                () -> assertEquals(2, list.size()),
                () -> assertEquals("설악산", list.get(0).name()),
                () -> assertEquals("지리산", list.get(1).name()),
                () -> assertEquals(1708, list.get(0).altitude()),
                () -> assertEquals(1915, list.get(1).altitude()),
                () -> assertEquals("desc1", list.get(0).description()),
                () -> assertEquals("desc2", list.get(1).description()),

                () -> assertEquals("url1", list.get(0).imageUrl()),
                () -> assertEquals("url2", list.get(1).imageUrl()),

                () -> assertEquals(1, list.get(0).grid().id()),
                () -> assertEquals(100, list.get(0).grid().x()),
                () -> assertEquals(200, list.get(0).grid().y()),

                () -> assertEquals(1, list.get(1).grid().id()),
                () -> assertEquals(100, list.get(1).grid().x()),
                () -> assertEquals(200, list.get(1).grid().y())
        );
    }
}