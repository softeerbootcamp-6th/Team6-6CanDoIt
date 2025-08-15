package com.softeer.mountain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MountainForecastApiResponse(
        @JsonProperty("baseDate") String baseDate,
        @JsonProperty("bastTime") String baseTime,
        @JsonProperty("category") String category,
        @JsonProperty("fcstBase") String forecastDate,
        @JsonProperty("fcstTime") String forecastTime,
        @JsonProperty("fcstValue") String forecastValue,
        @JsonProperty("nx") int nx,
        @JsonProperty("ny") int ny,
        @JsonProperty("lat") String lat,
        @JsonProperty("lon") String lon,
        @JsonProperty("alt") String alt,
        @JsonProperty("stn_nm") String stationName
) {
}
