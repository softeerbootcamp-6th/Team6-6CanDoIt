package com.softeer.repository;

import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;

import java.time.LocalDate;
import java.util.List;

public interface MountainAdapter {

    CoursePlan findCoursePlanByIdAndDate(long courseId, LocalDate date);
    List<Mountain> findAllMountains();
    Mountain findMountainById(long id);
}
