package com.softeer.common;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.softeer.config.ForecastApiType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;

public abstract class AbstractKmaApiCaller<R> implements KmaApiCaller<R> {

    // 재시도 가능한 에러 코드들
    protected static final Set<String> RETRYABLE_ERROR_CODES = Set.of(
            "LIMITED_NUMBER_OF_SERVICE_REQUESTS_PER_SECOND_EXCEEDS_ERROR",
            "HTTP_ROUTING_ERROR"
    );

    protected final RestClient restClient;
    protected final ForecastApiType forecastApiType;
    protected final XmlMapper xmlMapper;

    protected AbstractKmaApiCaller(
            RestClient restClient,
            ForecastApiType forecastApiType,
            XmlMapper xmlMapper
    ) {
        this.restClient = restClient;
        this.forecastApiType = forecastApiType;
        this.xmlMapper = xmlMapper;
    }

    protected abstract String getApiPath();

    @Override
    public abstract <T extends ApiRequest> List<R> call(T request);
}
