package com.softeer.repository.impl;

import com.softeer.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long> {

    @Query("SELECT c " +
            "FROM CourseEntity c " +
            "WHERE c.mountainEntity.id = :id")
    List<CourseEntity> findEntitiesByMountainId(long id);
}
