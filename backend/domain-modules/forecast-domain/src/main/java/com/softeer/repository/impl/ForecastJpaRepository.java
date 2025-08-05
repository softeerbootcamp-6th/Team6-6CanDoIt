package com.softeer.repository.impl;

import com.softeer.entity.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastJpaRepository extends JpaRepository<ForecastEntity, Long> {
}
