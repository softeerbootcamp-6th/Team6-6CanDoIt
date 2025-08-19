package com.softeer.service.impl;

import com.softeer.domain.Report;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.report.ReportQueryAdapter;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;
import com.softeer.service.ReportQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportQueryUseCaseImpl  implements ReportQueryUseCase {

    private final ReportQueryAdapter reportQueryAdapter;

    @Override
    public List<Report> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter, long courseId, ReportType reportType) {
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
}
