package com.softeer.common;

import com.softeer.config.ForecastApiType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;

public abstract class AbstractKmaApiCaller<R> implements KmaApiCaller<R> {

    protected static final Set<String> RETRYABLE_ERROR_CODES = Set.of(
            "22",
            "04"
    );

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
