package com.softeer.mapper;

import com.softeer.domain.Course;
import com.softeer.entity.CourseEntity;
import com.softeer.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "imageEntity", target = "imageUrl",
            qualifiedByName = "imageEntityToUrl")
    Course toDomain(CourseEntity entity);

    List<Course> toDomainList(List<CourseEntity> entities);

    @Named("imageEntityToUrl")
    default String mapImage(ImageEntity imageEntity) {
        return imageEntity.getImageUrl();
    }
}
