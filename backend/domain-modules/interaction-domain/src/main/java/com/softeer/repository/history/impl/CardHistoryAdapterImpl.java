package com.softeer.repository.history.impl;

import com.softeer.domain.CardHistory;
import com.softeer.entity.CardHistoryEntity;
import com.softeer.repository.history.CardHistoryAdapter;
import com.softeer.repository.support.pageable.CardHistoryPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardHistoryAdapterImpl implements CardHistoryAdapter {

    private final CardHistoryQuerydslRepository cardHistoryQuerydslRepository;
    private final CardHistoryJpaRepository cardHistoryJpaRepository;

    @Override
    public List<CardHistory> findUserCardHistory(long userId, CardHistoryPageable pageable) {
        return  cardHistoryQuerydslRepository.findUserCardHistory(userId, pageable);
    }

    @Override
    @Transactional
    public void touchOrCreate(long userId, long courseId, LocalDateTime forecastDate) {
        cardHistoryJpaRepository.findByUserIdAndCourseId(userId, courseId)
                .ifPresentOrElse(
                        CardHistoryEntity::touch,
                        () -> cardHistoryJpaRepository.save(CardHistoryEntity.builder()
                                .userId(userId)
                                .courseId(courseId)
                                .forecastDate(forecastDate)
                                .updatedAt(LocalDateTime.now())
                                .build())
                );
    }
}
