package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Body(
        @JsonProperty("dataType") String dataType,
        @JsonProperty("items") Items items,
        @JsonProperty("pageNo") int pageNo,
        @JsonProperty("numOfRows") int numOfRows,
        @JsonProperty("totalCount") int totalCount
) {

    public List<Item> getItems() {
        return items.itemList();
    }
}

