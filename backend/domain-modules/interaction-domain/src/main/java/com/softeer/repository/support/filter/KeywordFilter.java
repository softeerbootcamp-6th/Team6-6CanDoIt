package com.softeer.repository.support.filter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.softeer.repository.support.Where;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KeywordFilter implements Where {

    private final Where weatherFilter;
    private final Where rainFilter;
    private final Where etceteraFilter;

    public KeywordFilter(List<Integer> weatherKeywordIds,
                         List<Integer> rainKeywordIds,
                         List<Integer> etceteraKeywordIds) {
        // null-safe
        weatherKeywordIds  = Objects.isNull(weatherKeywordIds)  ? Collections.emptyList() : weatherKeywordIds;
        rainKeywordIds     = Objects.isNull(rainKeywordIds)     ? Collections.emptyList() : rainKeywordIds;
        etceteraKeywordIds = Objects.isNull(etceteraKeywordIds) ? Collections.emptyList() : etceteraKeywordIds;

        // 개별 필터 구성
        this.weatherFilter  = new WeatherFilter(weatherKeywordIds);
        this.rainFilter     = new RainFilter(rainKeywordIds);
        this.etceteraFilter = new EtceteraFilter(etceteraKeywordIds); // 기존 구현 재사용
    }

    @Override
    public Predicate where() {
        BooleanBuilder b = new BooleanBuilder();

        Predicate w = weatherFilter.where();
        if (w != null) b.and(w);

        Predicate r = rainFilter.where();
        if (r != null) b.and(r);

        Predicate e = etceteraFilter.where();
        if (e != null) b.and(e);

        return b.getValue();
    }
}
