package com.softeer.common;

import com.softeer.config.ForecastApiType;
import org.springframework.web.client.RestClient;

import java.util.List;

public abstract class AbstractKmaApiCaller<R> implements KmaApiCaller<R> {

    private final RestClient restClient;
    private final ForecastApiType forecastApiType;

    protected AbstractKmaApiCaller(
            RestClient restClient,
            ForecastApiType forecastApiType
    ) {
        this.restClient = restClient;
        this.forecastApiType = forecastApiType;
    }

    protected abstract String getApiPath();

    @Override
    public <T extends ApiRequest> List<R> call(T request) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(getApiPath())
                        .query(request.queryString())
                        .build())
                .retrieve()
                .body(forecastApiType.getResponseListType());
    }
}
