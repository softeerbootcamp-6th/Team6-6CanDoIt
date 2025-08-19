package com.softeer.repository.hisotry.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.softeer.domain.CardHistory;
import com.softeer.repository.support.pageable.CardHistoryPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.softeer.entity.QCardHistoryEntity.*;
import static com.softeer.entity.QCourseEntity.*;
import static com.softeer.entity.QUserEntity.*;

@Repository
@RequiredArgsConstructor
public class CardHistoryQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public List<CardHistory> findUserCardHistory(long userId, CardHistoryPageable pageable) {
        return queryFactory.select(
                        Projections.constructor(CardHistory.class,
                                cardHistoryEntity.id,
                                userEntity.id,
                                courseEntity.id,
                                cardHistoryEntity.forecastDate,
                                cardHistoryEntity.updatedAt
                                )
                )
                .from(cardHistoryEntity)
                .innerJoin(userEntity).on(userEntity.id.eq(cardHistoryEntity.userId))
                .innerJoin(courseEntity).on(courseEntity.id.eq(cardHistoryEntity.courseId))
                .where(
                        userEntity.id.eq(userId),
                        pageable.where()
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();
    }
}
