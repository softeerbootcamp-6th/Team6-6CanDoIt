package com.softeer.repository.report.impl;

import com.softeer.domain.Report;
import com.softeer.entity.enums.ReportType;
import com.softeer.entity.keyword.EtceteraKeywordEntity;
import com.softeer.entity.keyword.RainKeywordEntity;
import com.softeer.entity.keyword.WeatherKeywordEntity;
import com.softeer.repository.report.ReportQueryAdapter;
import com.softeer.repository.report.impl.jpa.EtceteraKeywordJpaRepository;
import com.softeer.repository.report.impl.jpa.RainKeywordJpaRepository;
import com.softeer.repository.report.impl.jpa.WeatherKeywordJpaRepository;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportQueryAdapterImpl implements ReportQueryAdapter {


    private final ReportQuerydslRepository reportQuerydslRepository;
    private final WeatherKeywordJpaRepository weatherKeywordJpaRepository;
    private final RainKeywordJpaRepository rainKeywordJpaRepository;
    private final EtceteraKeywordJpaRepository etceteraKeywordJpaRepository;


    @Override
    public List<Report> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter, long courseId, ReportType reportType, long userId) {
        return reportQuerydslRepository.findReportsByCourseIdAndType(pageable, keywordFilter, courseId, reportType, userId);
    }

    @Override
    public List<Report> findMyReports(ReportPageable reportPageable, long userId) {
        return reportQuerydslRepository.findMyReports(reportPageable, userId);
    }

    @Override
    public List<Report> findLikedReports(ReportPageable reportPageable, long userId) {
        return reportQuerydslRepository.findLikedReports(reportPageable, userId);
    }

    @Override
    public List<WeatherKeywordEntity> findAllWeatherKeywords() {
        return weatherKeywordJpaRepository.findAll();
    }

    @Override
    public List<RainKeywordEntity> findAllRainKeywords() {
        return rainKeywordJpaRepository.findAll();
    }

    @Override
    public List<EtceteraKeywordEntity> findAllEtceteraKeywords() {
        return etceteraKeywordJpaRepository.findAll();
    }
}
