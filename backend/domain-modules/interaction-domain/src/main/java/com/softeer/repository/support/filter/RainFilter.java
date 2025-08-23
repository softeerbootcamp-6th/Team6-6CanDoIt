package com.softeer.repository.support.filter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.softeer.repository.support.Where;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.softeer.entity.keyword.QReportRainKeywordEntity.*;

public class RainFilter implements Where{

    private final List<Integer> rainKeywordIds;

    public RainFilter(List<Integer> rainKeywordIds) {
        this.rainKeywordIds = Objects.isNull(rainKeywordIds) ? Collections.emptyList() : rainKeywordIds;
    }

    @Override
    public Predicate where() {
        if(rainKeywordIds.isEmpty()) return null;

        BooleanBuilder builder = new BooleanBuilder();
        for (Integer rainKeywordId : rainKeywordIds) {
            builder.and(reportRainKeywordEntity.id.rainKeywordId.eq(rainKeywordId));
        }
        return builder;
    }
}
