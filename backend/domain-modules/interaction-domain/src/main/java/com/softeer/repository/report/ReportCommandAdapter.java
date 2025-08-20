package com.softeer.repository.report;

import com.softeer.service.ReportCommandUseCase;

public interface ReportCommandAdapter {
    long saveReport(ReportCommandUseCase.ReportCreateDto reportCreateDto);
}
