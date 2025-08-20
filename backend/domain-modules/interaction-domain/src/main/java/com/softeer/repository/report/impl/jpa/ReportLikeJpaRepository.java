package com.softeer.repository.report.impl.jpa;

import com.softeer.entity.ReportLikeEntity;
import com.softeer.entity.id.ReportLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportLikeJpaRepository extends JpaRepository<ReportLikeEntity, ReportLikeId> {
}
