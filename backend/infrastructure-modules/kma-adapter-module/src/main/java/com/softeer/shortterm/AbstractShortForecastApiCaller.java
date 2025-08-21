package com.softeer.shortterm;

import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.common.ApiRequest;
import com.softeer.config.ForecastApiType;
import com.softeer.shortterm.dto.response.ShortForecastApiResponse;
import com.softeer.shortterm.dto.response.ShortForecastBody;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import com.softeer.throttle.ex.ThrottleException;
import com.softeer.throttle.ex.ThrottleExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class AbstractShortForecastApiCaller extends AbstractKmaApiCaller<ShortForecastItem> {

    protected AbstractShortForecastApiCaller(WebClient webClient) {
        super(webClient, ForecastApiType.SHORT_TERM);
    }

    @Override
    public <T extends ApiRequest> List<ShortForecastItem> call(T request) {
        ShortForecastApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(getApiPath())
                        .query(request.queryString())
                        .build())
                .retrieve()
                .bodyToMono(ShortForecastApiResponse.class)
                .block();

        if(Objects.isNull(response)) throw new ThrottleException(ThrottleExceptionStatus.NO_RETRY);

        String resultCode = response.getHeader().resultCode();

        if(!resultCode.equals("00") && RETRYABLE_ERROR_CODES.contains(resultCode)) {
            throw new ThrottleException(ThrottleExceptionStatus.RETRY);
        }

        return Optional.of(response)
                .map(ShortForecastApiResponse::getBody)
                .map(ShortForecastBody::getItems)
                .orElseThrow(() -> new ThrottleException(ThrottleExceptionStatus.NO_RETRY));
    }
}
