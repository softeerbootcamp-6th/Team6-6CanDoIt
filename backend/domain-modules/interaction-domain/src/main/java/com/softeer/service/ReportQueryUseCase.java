package com.softeer.service;

import com.softeer.domain.Keyword;
import com.softeer.domain.Report;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;

import java.util.List;

public interface ReportQueryUseCase {
    List<Report> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter,
                                              long courseId, ReportType reportType);
    List<Report> findMyReports(ReportPageable reportPageable, long userId);
    List<Report> findLikedReports(ReportPageable reportPageable, long userId);

    KeywordGroup findAllKeywords();

    record KeywordGroup(List<Keyword> weatherKeywords, List<Keyword> rainKeywords, List<Keyword> etceteraKeywords) { }
}

