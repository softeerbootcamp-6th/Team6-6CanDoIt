package com.softeer.repository.support.filter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.softeer.repository.support.Where;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.softeer.entity.keyword.QEtceteraKeywordEntity.*;

public class EtceteraFilter implements Where {

    private final List<Integer> etceteraKeywordIds;

    public EtceteraFilter(List<Integer> etceteraKeywordIds) {
        this.etceteraKeywordIds = Objects.isNull(etceteraKeywordIds) ? Collections.emptyList() : etceteraKeywordIds;
    }

    @Override
    public Predicate where() {
        if(etceteraKeywordIds.isEmpty()) return null;

        BooleanBuilder builder = new BooleanBuilder();
        for (Integer etceteraKeywordId : etceteraKeywordIds) {
            builder.and(etceteraKeywordEntity.id.eq(etceteraKeywordId));
        }
        return builder;
    }
}
