package com.softeer.batch.forecast.mountain.service;

import com.softeer.KmaApiCaller;
import com.softeer.KmaApiCallerFactory;
import com.softeer.config.ForecastApiType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastApiService {

    private final KmaApiCallerFactory kmaApiCallerFactory;

    public <T extends Record> List<?> getForecast(ForecastApiType forecastApiType, T request) {
        KmaApiCaller<?, ?> rawCaller = kmaApiCallerFactory.getApiCaller(forecastApiType);

        @SuppressWarnings("unchecked")
        KmaApiCaller<T, ?> typedCaller = (KmaApiCaller<T, ?>) rawCaller;

        return typedCaller.call(request);
    }
}

