package com.softeer.common;

import com.softeer.config.ForecastApiType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;

public abstract class AbstractKmaApiCaller<R> implements KmaApiCaller<R> {

    protected static final Set<String> RETRYABLE_ERROR_MESSAGES = Set.of(
            "LIMITED_NUMBER_OF_SERVICE_REQUESTS_PER_SECOND_EXCEEDS_ERROR");

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
