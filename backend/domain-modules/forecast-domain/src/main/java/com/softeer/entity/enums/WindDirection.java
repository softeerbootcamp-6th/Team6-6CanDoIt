package com.softeer.entity.enums;

import lombok.Getter;

@Getter
public enum WindDirection {
    N   ("북풍"),
    NNE ("북북동풍"),
    NE  ("북동풍"),
    ENE ("동북동풍"),
    E   ("동풍"),
    ESE ("동남동풍"),
    SE  ("남동풍"),
    SSE ("남남동풍"),
    S   ("남풍"),
    SSW ("남남서풍"),
    SW  ("남서풍"),
    WSW ("서남서풍"),
    W   ("서풍"),
    WNW ("서북서풍"),
    NW  ("북서풍"),
    NNW ("북북서풍");

    private final String description;

    WindDirection(String description) {
        this.description = description;
    }
}
