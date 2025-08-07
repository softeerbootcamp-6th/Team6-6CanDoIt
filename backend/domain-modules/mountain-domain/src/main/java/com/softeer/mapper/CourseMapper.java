package com.softeer.mapper;

import com.softeer.domain.Course;
import com.softeer.entity.CourseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    List<Course> toDomainList(List<CourseEntity> entities);
}
