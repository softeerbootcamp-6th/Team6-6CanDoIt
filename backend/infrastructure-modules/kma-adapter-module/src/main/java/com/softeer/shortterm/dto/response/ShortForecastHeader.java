package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortForecastHeader(
        @JsonProperty("resultCode") String resultCode,
        @JsonProperty("resultMsg") String resultMsg
) {
}
