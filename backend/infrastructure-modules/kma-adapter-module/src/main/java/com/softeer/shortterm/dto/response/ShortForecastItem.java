package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortForecastItem(
        @JsonProperty("baseDate") String baseDate,
        @JsonProperty("baseTime") String baseTime,
        @JsonProperty("category") String category,
        @JsonProperty("fcstDate") String forecastDate,
        @JsonProperty("fcstTime") String forecastTime,
        @JsonProperty("fcstValue") String forecastValue,
        @JsonProperty("nx") int nx,
        @JsonProperty("ny") int ny
) {
}
