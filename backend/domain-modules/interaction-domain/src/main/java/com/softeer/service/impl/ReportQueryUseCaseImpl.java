package com.softeer.service.impl;

import com.softeer.domain.Keyword;
import com.softeer.domain.Report;
import com.softeer.entity.enums.ReportType;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.ReportException;
import com.softeer.repository.report.ReportQueryAdapter;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;
import com.softeer.service.ReportQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReportQueryUseCaseImpl implements ReportQueryUseCase {

    private final ReportQueryAdapter reportQueryAdapter;

    @Override
    public List<Report> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter, long courseId, ReportType reportType) {
        if (Objects.isNull(reportType)) throw ExceptionCreator.create(ReportException.NO_REPORT_TYPE);

        return reportQueryAdapter.findReportsByCourseIdAndType(pageable, keywordFilter, courseId, reportType);
    }

    @Override
    public List<Report> findMyReports(ReportPageable reportPageable, long userId) {
        return reportQueryAdapter.findMyReports(reportPageable, userId);
    }

    @Override
    public List<Report> findLikedReports(ReportPageable reportPageable, long userId) {
        return reportQueryAdapter.findLikedReports(reportPageable, userId);
    }

    @Override
    public KeywordGroup findAllKeywords() {
        List<Keyword> weatherKeywords = reportQueryAdapter.findAllWeatherKeywords()
                .stream()
                .map(keyword -> new Keyword(keyword.getId(), keyword.getKeyword()))
                .toList();

        List<Keyword> rainKeywords = reportQueryAdapter.findAllRainKeywords()
                .stream()
                .map(keyword -> new Keyword(keyword.getId(), keyword.getKeyword()))
                .toList();

        List<Keyword> etceteraKeywords = reportQueryAdapter.findAllEtceteraKeywords()
                .stream()
                .map(keyword -> new Keyword(keyword.getId(), keyword.getKeyword()))
                .toList();

        return new  KeywordGroup(weatherKeywords, rainKeywords, etceteraKeywords);
    }
}
