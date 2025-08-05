package com.softeer.entity.enums;

import lombok.Getter;

@Getter
public enum Sky {
    SUNNY("맑음", "맑은 하늘"),
    CLOUDY("구름 조금", "조금 흐린 하늘"),
    OVERCAST("흐림", "흐린 하늘");

    private final String description;
    private final String displayDescription;

    Sky(final String description, String displayDescription) {
        this.description = description;
        this.displayDescription = displayDescription;
    }
}
