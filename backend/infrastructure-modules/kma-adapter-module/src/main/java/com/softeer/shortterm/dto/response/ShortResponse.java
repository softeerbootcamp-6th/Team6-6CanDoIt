package com.softeer.shortterm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortResponse(
        @JsonProperty("header") Header header,
        @JsonProperty("body") Body body
) {
}
