package com.softeer.service;

import com.softeer.entity.enums.ReportType;

import java.util.List;

public interface ReportCommandUseCase {
    void saveReport(ReportCreateDto reportCreateDto);

    record ReportCreateDto(
            long userId,
            long courseId,
            ReportType type,
            String content,
            List<Integer> weatherKeywords,
            List<Integer> rainKeywords,
            List<Integer> etceteraKeywords,
            long imageId
    ) {
    }
}
