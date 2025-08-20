package com.softeer.repository.report.impl;

import com.softeer.domain.Report;
import com.softeer.dto.ReportCreateDto;
import com.softeer.entity.ReportEntity;
import com.softeer.repository.report.ReportJpaAdapter;
import com.softeer.repository.report.impl.jpa.ReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReportJpaAdapterImpl implements ReportJpaAdapter {

    private final ReportJpaRepository reportJpaRepository;

    @Override
    public long saveReport(ReportCreateDto reportCreateDto) {
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
