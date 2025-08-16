package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Items(
        @JsonProperty("item") List<Item> itemList
) {
}
