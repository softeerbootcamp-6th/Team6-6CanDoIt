package com.softeer.dto.request;

import com.softeer.entity.enums.ReportType;

import java.util.List;

public record ReportCreateRequest(
        long courseId,
        ReportType type,
        String content,
        List<Integer> weatherKeywords,
        List<Integer> rainKeywords,
        List<Integer> etceteraKeywords
) {
}
