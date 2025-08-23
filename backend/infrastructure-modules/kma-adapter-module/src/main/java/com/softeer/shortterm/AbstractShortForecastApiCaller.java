package com.softeer.shortterm;

import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.common.ApiRequest;
import com.softeer.config.ForecastApiType;
import com.softeer.shortterm.dto.response.ShortForecastApiResponse;
import com.softeer.shortterm.dto.response.ShortForecastBody;
import com.softeer.shortterm.dto.response.ShortForecastHeader;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import com.softeer.throttle.ex.ThrottleException;
import com.softeer.throttle.ex.ThrottleExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class AbstractShortForecastApiCaller extends AbstractKmaApiCaller<ShortForecastItem> {

    protected AbstractShortForecastApiCaller(RestClient restClient) {
        super(restClient, ForecastApiType.SHORT_TERM);
    }

    @Override
    public <T extends ApiRequest> List<ShortForecastItem> call(T request) {
        ShortForecastApiResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(getApiPath())
                        .query(request.queryString())
                        .build())
                .retrieve()
                .body(ShortForecastApiResponse.class);

        if(Objects.isNull(response)) throw new ThrottleException(ThrottleExceptionStatus.NO_RETRY);

        ShortForecastHeader header = response.getHeader();
        String resultCode = header.resultCode();
        String resultMsg= header.resultMsg();

        if(!resultCode.equals("00") && RETRYABLE_ERROR_MESSAGES.contains(resultMsg)) {
            throw new ThrottleException(ThrottleExceptionStatus.RETRY);
        }

        return Optional.of(response)
                .map(ShortForecastApiResponse::getBody)
                .map(ShortForecastBody::getItems)
                .orElseThrow(() -> new ThrottleException(ThrottleExceptionStatus.NO_RETRY));
    }
}
