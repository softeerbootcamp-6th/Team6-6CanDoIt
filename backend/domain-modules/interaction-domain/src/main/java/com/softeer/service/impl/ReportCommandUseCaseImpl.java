package com.softeer.service.impl;

import com.softeer.entity.id.ReportEtceteraKeywordId;
import com.softeer.entity.id.ReportRainKeywordId;
import com.softeer.entity.id.ReportWeatherKeywordId;
import com.softeer.entity.keyword.ReportEtceteraKeywordEntity;
import com.softeer.entity.keyword.ReportRainKeywordEntity;
import com.softeer.entity.keyword.ReportWeatherKeywordEntity;
import com.softeer.repository.report.ReportCommandAdapter;
import com.softeer.repository.report.impl.jpa.ReportEtceteraKeywordRepository;
import com.softeer.repository.report.impl.jpa.ReportRainKeywordJpaRepository;
import com.softeer.repository.report.impl.jpa.ReportWeatherKeywordRepository;
import com.softeer.service.ReportCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportCommandUseCaseImpl implements ReportCommandUseCase {

    private final ReportCommandAdapter reportCommandAdapter;
    private final ReportWeatherKeywordRepository reportWeatherKeywordRepository;
    private final ReportRainKeywordJpaRepository reportRainKeywordRepository;
    private final ReportEtceteraKeywordRepository reportEtceteraKeywordRepository;

    @Override
    public void saveReport(ReportCreateDto reportCreateDto) {
        long reportId = reportCommandAdapter.saveReport(reportCreateDto);

        saveWeatherKeywords(reportCreateDto, reportId);
        saveRainKeywords(reportCreateDto, reportId);
        saveEtceteraKeywords(reportCreateDto, reportId);
    }

    private void saveWeatherKeywords(ReportCreateDto reportCreateDto, long reportId) {
        List<Integer> weatherKeywordIds = reportCreateDto.weatherKeywords();
        if (weatherKeywordIds != null && !weatherKeywordIds.isEmpty()) {
            List<ReportWeatherKeywordEntity> weatherEntities = weatherKeywordIds.stream()
                    .map(keywordId -> ReportWeatherKeywordEntity.builder()
                            .id(new ReportWeatherKeywordId(reportId, keywordId))
                            .build())
                    .toList();
            reportWeatherKeywordRepository.saveAll(weatherEntities);
        }
    }

    private void saveRainKeywords(ReportCreateDto reportCreateDto, long reportId) {
        List<Integer> rainKeywordIds = reportCreateDto.rainKeywords();
        if (rainKeywordIds != null && !rainKeywordIds.isEmpty()) {
            List<ReportRainKeywordEntity> rainEntities = rainKeywordIds.stream()
                    .map(keywordId -> ReportRainKeywordEntity.builder()
                            .id(new ReportRainKeywordId(reportId, keywordId))
                            .build())
                    .toList();
            reportRainKeywordRepository.saveAll(rainEntities);
        }
    }

    private void saveEtceteraKeywords(ReportCreateDto reportCreateDto, long reportId) {
        List<Integer> etceteraKeywordIds = reportCreateDto.etceteraKeywords();
        if (etceteraKeywordIds != null && !etceteraKeywordIds.isEmpty()) {
            List<ReportEtceteraKeywordEntity> etceteraEntities = etceteraKeywordIds.stream()
                    .map(keywordId -> ReportEtceteraKeywordEntity.builder()
                            .id(new ReportEtceteraKeywordId(reportId, keywordId))
                            .build())
                    .toList();
            reportEtceteraKeywordRepository.saveAll(etceteraEntities);
        }
    }


}
