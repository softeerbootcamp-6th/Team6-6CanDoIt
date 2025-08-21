package com.softeer.repository.history;

import com.softeer.domain.CardHistory;
import com.softeer.repository.support.pageable.CardHistoryPageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CardHistoryAdapter {
    List<CardHistory> findUserCardHistory(long userId, CardHistoryPageable pageable);
    void touchOrCreate(long userId, long courseId, LocalDateTime forecastDate);
}
