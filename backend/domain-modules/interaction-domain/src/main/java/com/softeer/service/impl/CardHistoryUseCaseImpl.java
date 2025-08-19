package com.softeer.service.impl;

import com.softeer.domain.CardHistory;
import com.softeer.repository.history.CardHistoryAdapter;
import com.softeer.repository.support.pageable.CardHistoryPageable;
import com.softeer.service.CardHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardHistoryUseCaseImpl implements CardHistoryUseCase {

    private final CardHistoryAdapter cardHistoryAdapter;

    @Override
    public List<CardHistory> findUserCardHistory(long userId, CardHistoryPageable pageable) {
        return cardHistoryAdapter.findUserCardHistory(userId, pageable);
    }
}
