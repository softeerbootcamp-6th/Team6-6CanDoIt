package com.softeer.repository.history.impl;

import com.softeer.entity.CardHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CardHistoryJpaRepository extends JpaRepository<CardHistoryEntity, Long> {
    Optional<CardHistoryEntity> findByUserIdAndCourseIdAndForecastDate(long userId, long courseId, LocalDateTime forecastDate);
}
