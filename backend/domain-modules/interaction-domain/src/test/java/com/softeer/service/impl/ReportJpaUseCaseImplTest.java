package com.softeer.service.impl;

import com.softeer.dto.ReportCreateDto;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.report.ReportJpaAdapter;
import com.softeer.repository.report.impl.jpa.ReportEtceteraKeywordRepository;
import com.softeer.repository.report.impl.jpa.ReportRainKeywordJpaRepository;
import com.softeer.repository.report.impl.jpa.ReportWeatherKeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito 기능을 JUnit 5 테스트에 활성화
class ReportJpaUseCaseImplTest {

    @InjectMocks // 테스트 대상 클래스. @Mock으로 생성된 가짜 객체들이 여기에 주입됩니다.
    private ReportJpaUseCaseImpl reportJpaUseCase;

    @Mock
    private ReportJpaAdapter reportJpaAdapter;

    @Mock
    private ReportWeatherKeywordRepository reportWeatherKeywordRepository;

    @Mock
    private ReportRainKeywordJpaRepository reportRainKeywordRepository;

    @Mock
    private ReportEtceteraKeywordRepository reportEtceteraKeywordRepository;

    @Test
    @DisplayName("모든 키워드를 포함한 신고(Report) 저장 성공 테스트")
    void saveReport_Success_WithAllKeywords() {
        // given
        ReportCreateDto dto = new ReportCreateDto(
                1L,
                101L,
                ReportType.SAFE,
                "테스트 내용입니다.",
                List.of(1, 2),
                List.of(3, 4),
                List.of(5, 6),
                201L
        );
        long expectedReportId = 1L;

        when(reportJpaAdapter.saveReport(any(ReportCreateDto.class))).thenReturn(expectedReportId);

        // when
        reportJpaUseCase.saveReport(dto);

        // then
        verify(reportJpaAdapter, times(1)).saveReport(dto);

        verify(reportWeatherKeywordRepository, times(1)).saveAll(anyList());
        verify(reportRainKeywordRepository, times(1)).saveAll(anyList());
        verify(reportEtceteraKeywordRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("키워드가 없는 신고(Report) 저장 성공 테스트")
    void saveReport_Success_WithNoKeywords() {
        // given
        ReportCreateDto dto = new ReportCreateDto(
                1L,
                101L,
                ReportType.SAFE,
                "키워드가 없는 테스트입니다.",
                List.of(),
                null,
                List.of(),
                202L
        );
        long expectedReportId = 2L;

        when(reportJpaAdapter.saveReport(any(ReportCreateDto.class))).thenReturn(expectedReportId);

        // when
        reportJpaUseCase.saveReport(dto);

        // then
        verify(reportJpaAdapter, times(1)).saveReport(dto);

        verify(reportWeatherKeywordRepository, never()).saveAll(anyList());
        verify(reportRainKeywordRepository, never()).saveAll(anyList());
        verify(reportEtceteraKeywordRepository, never()).saveAll(anyList());
    }
}