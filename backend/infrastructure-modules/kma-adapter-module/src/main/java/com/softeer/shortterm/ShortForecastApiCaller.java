package com.softeer.shortterm;

import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.common.ApiRequest;
import com.softeer.config.ForecastApiType;
import com.softeer.shortterm.dto.response.Body;
import com.softeer.shortterm.dto.response.Item;
import com.softeer.shortterm.dto.response.ShortForecastApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ShortForecastApiCaller extends AbstractKmaApiCaller<Item> {

    public ShortForecastApiCaller(RestClient restClient) {
        super(restClient, ForecastApiType.SHORT_TERM);
    }

    @Override
    protected String getApiPath() {
        return "/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    }

    @Override
    public <T extends ApiRequest> List<Item> call(T request) {
        URI uri = UriComponentsBuilder
                .fromPath(getApiPath())
                .query(request.queryString())
                .build(true)
                .toUri();

        ShortForecastApiResponse response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(ShortForecastApiResponse.class);

        if (response != null && response.getHeader() != null && "00".equals(response.getHeader().resultCode())) {
            return Optional.of(response)
                    .map(ShortForecastApiResponse::getBody)
                    .map(Body::getItems)
                    .orElse(Collections.emptyList());
        } else {
            return Collections.emptyList();
        }
    }
}
