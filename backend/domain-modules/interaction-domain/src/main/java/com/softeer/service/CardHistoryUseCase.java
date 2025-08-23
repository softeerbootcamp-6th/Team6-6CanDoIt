package com.softeer.service;

import com.softeer.domain.CardHistory;
import com.softeer.repository.support.pageable.CardHistoryPageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CardHistoryUseCase {
    List<CardHistory> findUserCardHistory(long userId, CardHistoryPageable pageable);
    void upsertCardHistory(long userId, long courseId, LocalDateTime forecastDate);
}
