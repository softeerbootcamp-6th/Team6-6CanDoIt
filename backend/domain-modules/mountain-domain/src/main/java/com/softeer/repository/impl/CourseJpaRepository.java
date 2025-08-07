package com.softeer.repository.impl;

import com.softeer.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {

    @Query(value = "SELECT * " +
            "FROM course c " +
            "WHERE c.mountain_id = :id",
            nativeQuery = true)
    List<CourseEntity> findEntitiesByMountainId(long id);
}
