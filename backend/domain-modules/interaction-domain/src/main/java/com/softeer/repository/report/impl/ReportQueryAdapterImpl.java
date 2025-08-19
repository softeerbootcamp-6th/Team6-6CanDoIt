package com.softeer.repository.report.impl;

import com.softeer.domain.Report;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.report.ReportQueryAdapter;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportQueryAdapterImpl implements ReportQueryAdapter {


    private final ReportQuerydslRepository reportQuerydslRepository;

    @Override
    public List<Report> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter, long courseId, ReportType reportType) {
        return reportQuerydslRepository.findReportsByCourseIdAndType(pageable, keywordFilter, courseId, reportType);
    }

    @Override
    public List<Report> findMyReports(ReportPageable reportPageable, long userId) {
        return reportQuerydslRepository.findMyReports(reportPageable, userId);
    }

    @Override
    public List<Report> findLikedReports(ReportPageable reportPageable, long userId) {
        return reportQuerydslRepository.findLikedReports(reportPageable, userId);
    }
}
