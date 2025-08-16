package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortForecastApiResponse(
        @JsonProperty("response") ShortForecastResponsePayload response
) {

    public ShortForecastHeader getHeader() {
        return response.shortForecastHeader();
    }

    public ShortForecastBody getBody() {
        return response.shortForecastBody();
    }
}

