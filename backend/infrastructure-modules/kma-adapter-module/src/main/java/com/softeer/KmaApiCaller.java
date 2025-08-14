package com.softeer;

import com.softeer.config.ForecastApiType;

import java.util.List;

public interface KmaApiCaller<T, R> {

    ForecastApiType getForecastApiType();

    List<R> call(T request);
}

