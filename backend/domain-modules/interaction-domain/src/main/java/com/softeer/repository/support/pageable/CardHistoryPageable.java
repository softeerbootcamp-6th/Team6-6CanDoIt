package com.softeer.repository.support.pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.softeer.repository.support.AbstractPageable;

import java.util.Optional;

import static com.softeer.entity.QCardHistoryEntity.*;

public class CardHistoryPageable extends AbstractPageable {

    public CardHistoryPageable(int pageSize, Long lastId) {
        super(pageSize, lastId);
    }

    public static CardHistoryPageable of(Integer pageSize, Long lastId) {
        return new CardHistoryPageable(
                Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE),
                Optional.ofNullable(lastId).orElse(DEFAULT_LAST_ID)
        );    }

    @Override
    public OrderSpecifier<?>[] orderBy() {
        return new OrderSpecifier[]{cardHistoryEntity.updatedAt.desc()};
    }

    @Override
    public Predicate where() {
        return cardHistoryEntity.id.lt(lastId);
    }
}
