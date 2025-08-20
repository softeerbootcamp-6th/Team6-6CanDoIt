package com.softeer.repository.report;

import com.softeer.dto.ReportCreateDto;
import com.softeer.entity.ReportEntity;

public interface ReportJpaAdapter {
    long saveReport(ReportCreateDto reportCreateDto);
}
