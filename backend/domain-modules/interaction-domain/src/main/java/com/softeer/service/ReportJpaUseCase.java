package com.softeer.service;

import com.softeer.dto.ReportCreateDto;

public interface ReportJpaUseCase {
    void saveReport(ReportCreateDto reportCreateDto);
}
