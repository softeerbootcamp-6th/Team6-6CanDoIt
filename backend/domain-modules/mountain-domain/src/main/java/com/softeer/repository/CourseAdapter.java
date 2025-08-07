package com.softeer.repository;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;

import java.time.LocalDate;
import java.util.List;

public interface CourseAdapter {

    CoursePlan findCoursePlanByIdAndDate(long courseId, LocalDate date);
    List<Course> findCoursesByMountainId(long id);
}
