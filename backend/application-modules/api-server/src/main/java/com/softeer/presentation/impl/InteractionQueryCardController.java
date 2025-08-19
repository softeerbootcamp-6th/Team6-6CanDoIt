package com.softeer.presentation.impl;

import com.softeer.domain.CardHistory;
import com.softeer.dto.response.card.ReportCardResponse;
import com.softeer.entity.enums.ReportType;
import com.softeer.presentation.InteractionQueryCardApi;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.CardHistoryPageable;
import com.softeer.repository.support.pageable.ReportPageable;
import com.softeer.service.InteractionQueryCardService;
import com.softeer.service.ReportQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InteractionQueryCardController implements InteractionQueryCardApi {

    private final InteractionQueryCardService interactionQueryCardService;

    @Override
    public ResponseEntity<ReportQueryUseCase.KeywordGroup> Keywords() {
        return ResponseEntity.ok(interactionQueryCardService.findAllKeywords());
    }

    @Override
    public ResponseEntity<List<ReportCardResponse>> reports(Integer pageSize, Long lastId, List<Integer> weatherKeywordIds, List<Integer> rainKeywordIds, List<Integer> etceteraKeywordIds, long courseId, ReportType reportType) {
        ReportPageable pageable = ReportPageable.of(pageSize, lastId);
        KeywordFilter keywordFilter = new KeywordFilter(weatherKeywordIds, rainKeywordIds, etceteraKeywordIds);

        return ResponseEntity.ok(interactionQueryCardService.findReportsByCourseIdAndType(pageable, keywordFilter, courseId, reportType));
    }

    @Override
    public ResponseEntity<List<ReportCardResponse>> myReports(Integer pageSize, Long lastId, Long userId) {
        ReportPageable pageable = ReportPageable.of(pageSize, lastId);

        return ResponseEntity.ok(interactionQueryCardService.findMyReports(pageable, userId));
    }

    @Override
    public ResponseEntity<List<ReportCardResponse>> likedReports(Integer pageSize, Long lastId, Long userId) {
        ReportPageable pageable = ReportPageable.of(pageSize, lastId);

        return ResponseEntity.ok(interactionQueryCardService.findLikedReports(pageable, userId));    }

    @Override
    public ResponseEntity<List<CardHistory>> myCardHistory(Integer pageSize, Long lastId, Long userId) {
        CardHistoryPageable pageable = CardHistoryPageable.of(pageSize, lastId);

        return ResponseEntity.ok(interactionQueryCardService.findUserCardHistory(pageable, userId));
    }
}
