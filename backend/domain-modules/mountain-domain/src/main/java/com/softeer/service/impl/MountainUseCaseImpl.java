package com.softeer.service.impl;

import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;
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

    @Override
    public CoursePlan getCoursePlan(long courseId, LocalDate date) {
        return mountainAdapter.findCoursePlanByIdAndDate(courseId, date);
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
