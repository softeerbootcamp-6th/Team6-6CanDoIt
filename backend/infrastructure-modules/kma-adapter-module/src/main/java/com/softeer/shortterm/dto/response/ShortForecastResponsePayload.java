package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortForecastResponsePayload(
        @JsonProperty("shortForecastHeader") ShortForecastHeader shortForecastHeader,
        @JsonProperty("body") ShortForecastBody shortForecastBody
) {
}
