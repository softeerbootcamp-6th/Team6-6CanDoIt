package com.softeer.repository.support.filter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.softeer.repository.support.Where;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.softeer.entity.keyword.QReportWeatherKeywordEntity.*;

public class WeatherFilter implements Where {
    private final List<Integer> weatherKeywordIds;

    public WeatherFilter(List<Integer> weatherKeywordIds) {
        this.weatherKeywordIds = Objects.isNull(weatherKeywordIds) ? Collections.emptyList() : weatherKeywordIds;
    }

    @Override
    public Predicate where() {
        if(weatherKeywordIds.isEmpty()) return null;

        BooleanBuilder builder = new BooleanBuilder();
        for (Integer weatherKeywordId : weatherKeywordIds) {
            builder.and(reportWeatherKeywordEntity.id.weatherKeywordId.eq(weatherKeywordId));
        }
        return builder;
    }
}
