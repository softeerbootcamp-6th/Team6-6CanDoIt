package com.softeer.repository.report.impl.jpa;

import com.softeer.entity.keyword.WeatherKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherKeywordJpaRepository extends JpaRepository<WeatherKeywordEntity, Integer> {
}
