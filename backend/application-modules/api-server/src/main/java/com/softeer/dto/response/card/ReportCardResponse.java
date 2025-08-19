package com.softeer.dto.response.card;

import com.softeer.domain.Report;
import com.softeer.entity.enums.*;
import com.softeer.entity.enums.EtceteraKeyword;
import com.softeer.entity.enums.RainKeyword;

import java.time.LocalDateTime;
import java.util.List;

public record ReportCardResponse(long reportId, ReportType reportType, LocalDateTime createdAt,
                                 String nickname, String userImageUrl,
                                 String imageUrl, String content, int likeCount,
                                 List<String> weatherKeywords, List<String> rainKeywords, List<String> etceteraKeywords
                                 ) {

    public static ReportCardResponse from(Report report) {
        List<String> weatherKeywords = report.weatherKeywords().stream()
                .map(WeatherKeyword::getDescription)
                .toList();

        List<String> rainKeywords = report.rainKeywords().stream()
                .map(RainKeyword::getDescription)
                .toList();

        List<String> etceteraKeywords = report.etceteraKeywords().stream()
                .map(EtceteraKeyword::getDescription)
                .toList();

        return new ReportCardResponse(report.id(), report.reportType(), report.createdAt(),
                report.nickname(), report.userImageUrl(),
                report.imageUrl(), report.content(), report.likeCount(),
                weatherKeywords, rainKeywords, etceteraKeywords
        );
    }
}
