package com.softeer.common;

import com.softeer.config.ForecastApiType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Set;

public abstract class AbstractKmaApiCaller<R> implements KmaApiCaller<R> {

    protected static final Set<String> RETRYABLE_ERROR_CODES = Set.of(
            "22",
            "04"
    );

    protected final WebClient webClient;
    protected final ForecastApiType forecastApiType;

    protected AbstractKmaApiCaller(
            WebClient webClient,
            ForecastApiType forecastApiType
    ) {
        this.webClient = webClient;
        this.forecastApiType = forecastApiType;
    }

    protected abstract String getApiPath();

    @Override
    public abstract <T extends ApiRequest> List<R> call(T request);
}
