package com.softeer.service.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;
import com.softeer.repository.CourseAdapter;
import com.softeer.repository.MountainAdapter;
import com.softeer.service.MountainUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainUseCaseImpl implements MountainUseCase {

    private final MountainAdapter mountainAdapter;
    private final CourseAdapter courseAdapter;

    @Override
    public CoursePlan getCoursePlan(long courseId, LocalDate date) {
        return courseAdapter.findCoursePlanByIdAndDate(courseId, date);
    }

    @Override
    public List<Course> getCoursesByMountainId(long mountainId) {
        return courseAdapter.findCoursesByMountainId(mountainId);
    }

    @Override
    public List<Mountain> getMountains() {
        return mountainAdapter.findAllMountains();
    }

    @Override
    public Mountain getMountainById(long id) {
        return mountainAdapter.findMountainById(id);
    }
}
