package com.softeer.repository.report.impl;

import com.softeer.entity.ReportEntity;
import com.softeer.entity.ReportLikeEntity;
import com.softeer.entity.id.ReportLikeId;
import com.softeer.repository.report.ReportCommandAdapter;
import com.softeer.repository.report.impl.jpa.ReportJpaRepository;
import com.softeer.repository.report.impl.jpa.ReportLikeJpaRepository;
import com.softeer.service.ReportCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReportCommandAdapterImpl implements ReportCommandAdapter {

    private final ReportJpaRepository reportJpaRepository;
    private final ReportLikeJpaRepository reportLikeJpaRepository;

    @Override
    public long saveReport(ReportCommandUseCase.ReportCreateDto reportCreateDto) {
        ReportEntity reportEntity = ReportEntity.builder()
                .type(reportCreateDto.type())
                .content(reportCreateDto.content())
                .createdAt(LocalDateTime.now())
                .userId(reportCreateDto.userId())
                .courseId(reportCreateDto.courseId())
                .imageId(reportCreateDto.imageId())
                .build();

        return reportJpaRepository.save(reportEntity).getId();
    }

    @Override
    public boolean existsReportLike(ReportLikeId reportLikeId) {
        return reportLikeJpaRepository.existsById(reportLikeId);
    }

    @Override
    @Transactional
    public void saveReportLike(ReportLikeId reportLikeId) {
        reportJpaRepository.plusLikeCount(reportLikeId.getReportId());
        reportLikeJpaRepository.save(ReportLikeEntity.builder()
                .id(reportLikeId)
                .build());
    }

    @Override
    @Transactional
    public void deleteReportLike(ReportLikeId reportLikeId) {
        reportJpaRepository.minusLikeCount(reportLikeId.getReportId());
        reportLikeJpaRepository.deleteById(reportLikeId);
    }
}
