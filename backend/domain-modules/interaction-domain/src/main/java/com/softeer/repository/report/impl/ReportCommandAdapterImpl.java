package com.softeer.repository.report.impl;

import com.softeer.entity.ReportEntity;
import com.softeer.repository.report.ReportCommandAdapter;
import com.softeer.repository.report.impl.jpa.ReportJpaRepository;
import com.softeer.service.ReportCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReportCommandAdapterImpl implements ReportCommandAdapter {

    private final ReportJpaRepository reportJpaRepository;

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
}
