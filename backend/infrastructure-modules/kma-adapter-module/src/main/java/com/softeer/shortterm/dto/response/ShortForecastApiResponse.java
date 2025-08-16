package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortForecastApiResponse(
        @JsonProperty("response") ShortResponse response
) {

    public Header getHeader() {
        return response.header();
    }

    public Body getBody() {
        return response.body();
    }
}

