package com.softeer.repository.report.impl.jpa;

import com.softeer.entity.id.ReportRainKeywordId;
import com.softeer.entity.keyword.ReportRainKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRainKeywordJpaRepository extends JpaRepository<ReportRainKeywordEntity, ReportRainKeywordId> {
}