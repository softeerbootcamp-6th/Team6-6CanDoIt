package com.softeer.repository.history.impl;

import com.softeer.entity.CardHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardHistoryJpaRepository extends JpaRepository<CardHistoryEntity, Long> {
    Optional<CardHistoryEntity> findByUserIdAndCourseId(long userId, long courseId);
}
