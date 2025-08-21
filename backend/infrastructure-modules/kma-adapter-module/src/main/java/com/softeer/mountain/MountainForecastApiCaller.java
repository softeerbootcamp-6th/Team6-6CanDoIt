package com.softeer.mountain;

import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.common.ApiRequest;
import com.softeer.config.ForecastApiType;
import com.softeer.mountain.dto.MountainForecastApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class MountainForecastApiCaller extends AbstractKmaApiCaller<MountainForecastApiResponse> {

    public MountainForecastApiCaller(WebClient webClient) {
        super(webClient, ForecastApiType.MOUNTAIN);
    }

    @Override
    protected String getApiPath() {
        return "/api/typ08/getMountainWeather";
    }

    @Override
    public <T extends ApiRequest> List<MountainForecastApiResponse> call(T request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(getApiPath())
                        .query(request.queryString())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<MountainForecastApiResponse>>() {})
                .block();
    }
}
