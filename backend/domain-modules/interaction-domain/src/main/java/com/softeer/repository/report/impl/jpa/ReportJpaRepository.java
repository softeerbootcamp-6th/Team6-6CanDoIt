package com.softeer.repository.report.impl.jpa;

import com.softeer.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Long> {
}
