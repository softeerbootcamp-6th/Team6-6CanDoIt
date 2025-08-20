package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShortForecastItem(
        @JsonProperty("baseDate") String baseDate,
        @JsonProperty("baseTime") String baseTime,
        @JsonProperty("category") String category,
        @JsonProperty("fcstDate") @JsonFormat(pattern = "yyyyMMdd") LocalDate forecastDate,
        @JsonProperty("fcstTime") @JsonFormat(pattern = "HHmm") LocalTime forecastTime,
        @JsonProperty("fcstValue") String forecastValue,
        @JsonProperty("nx") int nx,
        @JsonProperty("ny") int ny
) {
}
