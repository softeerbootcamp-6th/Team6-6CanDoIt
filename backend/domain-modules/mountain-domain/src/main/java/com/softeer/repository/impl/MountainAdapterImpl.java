package com.softeer.repository.impl;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Mountain;
import com.softeer.entity.CourseEntity;
import com.softeer.entity.MountainEntity;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.MountainException;
import com.softeer.mapper.CourseMapper;
import com.softeer.mapper.MountainMapper;
import com.softeer.repository.MountainAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MountainAdapterImpl implements MountainAdapter {

    private final CoursePlanJdbcRepository coursePlanJdbcRepository;
    private final MountainJpaRepository mountainJpaRepository;
    private final CourseJpaRepository courseJpaRepository;

    private final MountainMapper mountainMapper;
    private final CourseMapper courseMapper;

    @Override
    public CoursePlan findCoursePlanByIdAndDate(long courseId, LocalDate date) {
        return coursePlanJdbcRepository.findCoursePlan(courseId, date);
    }

    @Override
    public List<Mountain> findAllMountains() {
        List<MountainEntity> mountainEntities = mountainJpaRepository.findAll();

        return mountainMapper.toDomainList(mountainEntities);
    }

    @Override
    public Mountain findMountainById(long id) {
        MountainEntity mountainEntity = mountainJpaRepository.findById(id)
                .orElseThrow(() -> ExceptionCreator.create(MountainException.NOT_FOUND));

        return mountainMapper.toDomain(mountainEntity);
    }

    @Override
    public List<Course> findCoursesByMountainId(long id) {
        List<CourseEntity> courseEntities = courseJpaRepository.findEntitiesByMountainId(id);

        return courseMapper.toDomainList(courseEntities);
    }
}
