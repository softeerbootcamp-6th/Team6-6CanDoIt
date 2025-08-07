package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.entity.CourseEntity;
import com.softeer.mapper.CourseMapper;
import com.softeer.repository.CourseAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseAdapterImpl implements CourseAdapter {

    private final CoursePlanJdbcRepository coursePlanJdbcRepository;
    private final CourseJpaRepository courseJpaRepository;

    private final CourseMapper courseMapper;

    @Override
    public CoursePlan findCoursePlanByIdAndDate(long courseId, LocalDate date) {
        return coursePlanJdbcRepository.findCoursePlan(courseId, date);
    }

    @Override
    public List<Course> findCoursesByMountainId(long id) {
        List<CourseEntity> courseEntities = courseJpaRepository.findEntitiesByMountainId(id);

        return courseMapper.toDomainList(courseEntities);
    }
}
