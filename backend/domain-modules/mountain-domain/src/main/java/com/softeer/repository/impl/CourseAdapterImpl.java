package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.repository.CourseAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseAdapterImpl implements CourseAdapter {

    private final CoursePlanJdbcRepository coursePlanJdbcRepository;
    private final CourseJdbcRepository courseJdbcRepository;

    @Override
    public CoursePlan findCoursePlanByIdAndDate(long courseId, LocalDate date) {
        return coursePlanJdbcRepository.findCoursePlan(courseId, date);
    }

    @Override
    public List<Course> findCoursesByMountainId(long id) {
        return courseJdbcRepository.findCoursesByMountainId(id);
    }
}
