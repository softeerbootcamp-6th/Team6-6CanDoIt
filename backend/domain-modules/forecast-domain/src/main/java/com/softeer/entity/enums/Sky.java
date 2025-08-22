package com.softeer.entity.enums;

import lombok.Getter;

@Getter
public enum Sky {
    SUNNY("맑음", "화창"),
    CLOUDY("구름 조금", "구름 많음"),
    OVERCAST("흐림", "흐림");

    private final String description;
    private final String displayDescription;

    Sky(final String description, String displayDescription) {
        this.description = description;
        this.displayDescription = displayDescription;
    }
}
