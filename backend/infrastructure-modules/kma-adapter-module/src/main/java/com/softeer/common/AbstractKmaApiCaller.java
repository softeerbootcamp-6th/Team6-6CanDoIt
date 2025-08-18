package com.softeer.common;

import com.softeer.config.ForecastApiType;
import org.springframework.web.client.RestClient;

import java.util.List;

public abstract class AbstractKmaApiCaller<R> implements KmaApiCaller<R> {

    protected final RestClient restClient;
    protected final ForecastApiType forecastApiType;

    protected AbstractKmaApiCaller(
            RestClient restClient,
            ForecastApiType forecastApiType
    ) {
        this.restClient = restClient;
        this.forecastApiType = forecastApiType;
    }

    protected abstract String getApiPath();

    @Override
    public abstract <T extends ApiRequest> List<R> call(T request);
}
