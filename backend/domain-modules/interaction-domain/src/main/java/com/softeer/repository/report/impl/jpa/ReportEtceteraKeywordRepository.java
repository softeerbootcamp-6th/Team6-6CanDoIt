package com.softeer.repository.report.impl.jpa;

import com.softeer.entity.id.ReportEtceteraKeywordId;
import com.softeer.entity.keyword.ReportEtceteraKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportEtceteraKeywordRepository extends JpaRepository<ReportEtceteraKeywordEntity, ReportEtceteraKeywordId> {
}
