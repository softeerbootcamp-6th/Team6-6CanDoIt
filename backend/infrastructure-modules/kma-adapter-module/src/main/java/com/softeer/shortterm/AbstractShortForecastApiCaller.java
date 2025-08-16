package com.softeer.shortterm;

import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.common.ApiRequest;
import com.softeer.config.ForecastApiType;
import com.softeer.shortterm.dto.response.ShortForecastApiResponse;
import com.softeer.shortterm.dto.response.ShortForecastBody;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AbstractShortForecastApiCaller extends AbstractKmaApiCaller<ShortForecastItem> {

    protected AbstractShortForecastApiCaller(RestClient restClient) {
        super(restClient, ForecastApiType.SHORT_TERM);
    }

    @Override
    protected String getApiPath() {
        return "";
    }

    @Override
    public <T extends ApiRequest> List<ShortForecastItem> call(T request) {
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
                    .map(ShortForecastBody::getItems)
                    .orElse(Collections.emptyList());
        } else {
            return Collections.emptyList();
        }
    }
}
