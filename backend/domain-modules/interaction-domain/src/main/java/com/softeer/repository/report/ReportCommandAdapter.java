package com.softeer.repository.report;

import com.softeer.entity.id.ReportLikeId;
import com.softeer.service.ReportCommandUseCase;

public interface ReportCommandAdapter {
    long saveReport(ReportCommandUseCase.ReportCreateDto reportCreateDto);
    boolean existsReportLike(ReportLikeId reportLikeId);
    void saveReportLike(ReportLikeId reportLikeId);
    void deleteReportLike(ReportLikeId reportLikeId);
}
