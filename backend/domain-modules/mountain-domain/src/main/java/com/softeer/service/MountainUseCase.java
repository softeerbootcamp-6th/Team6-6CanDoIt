package com.softeer.service;

import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;

import java.time.LocalDate;
import java.util.List;

public interface MountainUseCase {
    CoursePlan getCoursePlan(long courseId, LocalDate date);
    List<Mountain> getMountains();
    Mountain getMountainById(long id);
}
