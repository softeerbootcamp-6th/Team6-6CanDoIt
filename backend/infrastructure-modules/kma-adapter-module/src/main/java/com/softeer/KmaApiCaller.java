package com.softeer;

import com.softeer.config.ForecastApiType;

import java.util.List;

public interface KmaApiCaller<T, R> {

    ForecastApiType getForecastApiType();
    Class<T> getRequestType();
    List<R> call(T request);
}

