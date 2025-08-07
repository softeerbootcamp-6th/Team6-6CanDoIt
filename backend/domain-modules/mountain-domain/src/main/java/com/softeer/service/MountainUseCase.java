package com.softeer.service;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;

import java.time.LocalDate;
import java.util.List;

public interface MountainUseCase {
    // Course
    CoursePlan getCoursePlan(long courseId, LocalDate date);
    List<Course> getCoursesByMountainId(long mountainId);

    // Mountain
    List<Mountain> getMountains();
    Mountain getMountainById(long id);
}
