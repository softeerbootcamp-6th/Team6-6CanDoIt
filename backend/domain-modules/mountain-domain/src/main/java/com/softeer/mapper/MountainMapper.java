package com.softeer.mapper;

import com.softeer.domain.Grid;
import com.softeer.domain.Mountain;
import com.softeer.entity.GridEntity;
import com.softeer.entity.ImageEntity;
import com.softeer.entity.MountainEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MountainMapper {

    @Mapping(source = "imageEntity", target = "imageUrl",
            qualifiedByName = "imageEntityToUrl")
    @Mapping(source = "gridEntity", target = "grid",
            qualifiedByName = "gridEntityToGrid")
    Mountain toDomain(MountainEntity entity);

    List<Mountain> toDomainList(List<MountainEntity> entities);

    @Named("imageEntityToUrl")               // 커스텀 헬퍼
    default String mapImage(ImageEntity imageEntity) {
        return imageEntity.getImageUrl();
    }

    @Named("gridEntityToGrid")
    default Grid toGrid(GridEntity gridEntity) {
        return new Grid(
                gridEntity.getId(),
                gridEntity.getX(),
                gridEntity.getY()
        );
    }
}
