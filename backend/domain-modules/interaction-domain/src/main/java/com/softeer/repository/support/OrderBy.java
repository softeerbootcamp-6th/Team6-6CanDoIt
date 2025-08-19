package com.softeer.repository.support;

import com.querydsl.core.types.OrderSpecifier;

public interface OrderBy {
    OrderSpecifier<?>[] orderBy();
}
