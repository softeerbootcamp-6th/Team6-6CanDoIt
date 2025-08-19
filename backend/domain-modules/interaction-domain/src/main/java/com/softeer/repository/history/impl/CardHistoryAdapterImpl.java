package com.softeer.repository.history.impl;

import com.softeer.domain.CardHistory;
import com.softeer.repository.history.CardHistoryAdapter;
import com.softeer.repository.support.pageable.CardHistoryPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardHistoryAdapterImpl implements CardHistoryAdapter {

    private final CardHistoryQuerydslRepository cardHistoryQuerydslRepository;


    @Override
    public List<CardHistory> findUserCardHistory(long userId, CardHistoryPageable pageable) {
        return  cardHistoryQuerydslRepository.findUserCardHistory(userId, pageable);
    }
}
