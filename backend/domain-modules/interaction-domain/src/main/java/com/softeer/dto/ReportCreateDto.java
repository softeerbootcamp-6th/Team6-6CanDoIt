package com.softeer.dto;

import com.softeer.entity.enums.ReportType;

import java.util.List;

public record ReportCreateDto(
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
