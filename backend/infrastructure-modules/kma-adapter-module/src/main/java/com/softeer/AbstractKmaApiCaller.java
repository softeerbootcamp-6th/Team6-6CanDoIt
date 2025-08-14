package com.softeer;

import com.softeer.config.ForecastApiType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.lang.reflect.RecordComponent;
import java.util.List;
import java.util.Objects;

public abstract class AbstractKmaApiCaller<T extends Record, R> implements KmaApiCaller<T, R> {

    private final RestClient restClient;
    private final String apiKey;
    private final Class<R> responseClass;
    private final ForecastApiType forecastApiType;

    protected AbstractKmaApiCaller(
            RestClient restClient,
            String apiKey,
            Class<R> responseClass,
            ForecastApiType forecastApiType
    ) {
        this.restClient = restClient;
        this.apiKey = apiKey;
        this.responseClass = responseClass;
        this.forecastApiType = forecastApiType;
    }

    protected abstract String getApiPath();

    @Override
    public abstract Class<T> getRequestType();

    @Override
    public ForecastApiType getForecastApiType() {
        return forecastApiType;
    }

    @Override
    public List<R> call(T request) {
        ResolvableType resolvableType = ResolvableType.forClassWithGenerics(List.class, responseClass);
        ParameterizedTypeReference<List<R>> typeReference = ParameterizedTypeReference.forType(resolvableType.getType());

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(getApiPath())
                        .queryParams(buildParamsFromRecord(request))
                        .build())
                .retrieve()
                .body(typeReference);
    }

    private MultiValueMap<String, String> buildParamsFromRecord(T request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("authKey", this.apiKey);

        for (RecordComponent component : request.getClass().getRecordComponents()) {
            try {
                Object value = component.getAccessor().invoke(request);

                if (Objects.nonNull(value)) {
                    params.add(component.getName(), value.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to build query params from record", e);
            }
        }
        return params;
    }
}
