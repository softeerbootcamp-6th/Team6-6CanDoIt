package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ShortForecastBody(
        @JsonProperty("dataType") String dataType,
        @JsonProperty("shortForecastItems") ShortForecastItems shortForecastItems,
        @JsonProperty("pageNo") int pageNo,
        @JsonProperty("numOfRows") int numOfRows,
        @JsonProperty("totalCount") int totalCount
) {

    public List<ShortForecastItem> getItems() {
        return shortForecastItems.shortForecastItemList();
    }
}

