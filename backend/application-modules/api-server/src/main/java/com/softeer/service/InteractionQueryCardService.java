package com.softeer.service;

import com.softeer.domain.CardHistory;
import com.softeer.domain.Report;
import com.softeer.dto.response.card.ReportCardResponse;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.CardHistoryPageable;
import com.softeer.repository.support.pageable.ReportPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InteractionQueryCardService {

    private final ReportQueryUseCase reportQueryUseCase;
    private final CardHistoryUseCase cardHistoryUseCase;

    public List<ReportCardResponse> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter, long courseId, ReportType reportType, Optional<Long> userId) {
        List<Report> reports = reportQueryUseCase.findReportsByCourseIdAndType(pageable, keywordFilter, courseId, reportType, userId);

        return createReportCard(reports);

    }
    public List<ReportCardResponse> findMyReports(ReportPageable reportPageable, long userId) {
        List<Report> reports = reportQueryUseCase.findMyReports(reportPageable, userId);

        return createReportCard(reports);
    }


    public List<ReportCardResponse> findLikedReports(ReportPageable reportPageable, long userId) {
        List<Report> reports = reportQueryUseCase.findLikedReports(reportPageable, userId);

        return createReportCard(reports);
    }


    private List<ReportCardResponse> createReportCard(List<Report> reports) {
        return reports.stream()
                .map(ReportCardResponse::from)
                .toList();
    }

    public List<CardHistory> findUserCardHistory(CardHistoryPageable pageable, Long userId) {
        return cardHistoryUseCase.findUserCardHistory(userId, pageable);
    }

    public ReportQueryUseCase.KeywordGroup findAllKeywords() {
        return reportQueryUseCase.findAllKeywords();
    }
}
