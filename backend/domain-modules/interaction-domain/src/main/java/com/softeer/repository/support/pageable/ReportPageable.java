package com.softeer.repository.support.pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.softeer.repository.support.AbstractPageable;

import java.util.Optional;

import static com.softeer.entity.QReportEntity.*;

public class ReportPageable extends AbstractPageable {

    public ReportPageable(int pageSize, Long lastId) {
        super(pageSize, lastId);
    }


    public static ReportPageable of(Integer pageSize, Long lastId) {
        return new ReportPageable(
                Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE),
                Optional.ofNullable(lastId).orElse(DEFAULT_LAST_ID)
        );
    }


    @Override
    public Predicate where() {
        return reportEntity.id.lt(lastId);
    }

    @Override
    public OrderSpecifier<?>[] orderBy() {
        return new OrderSpecifier[]{reportEntity.id.desc()};
    }

    public int pageSize() {
        return pageSize;
    }
}
