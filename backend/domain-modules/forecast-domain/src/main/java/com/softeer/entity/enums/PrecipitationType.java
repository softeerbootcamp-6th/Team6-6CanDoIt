package com.softeer.entity.enums;

public enum PrecipitationType {
    NONE(0, "없음"),
    RAIN(1, "비"),
    SLEET(2, "비/눈"),
    SNOW(3, "눈"),
    SHOWER(4, "소나기"),
    DRIZZLE(5, "빗방울"),
    DRIZZLE_SNOW(6, "빗방울눈날림"),
    SNOW_FLURRY(7, "눈날림");

    private final int code;
    private final String description;

    PrecipitationType(int code, String description) {
        this.code = code;
        this.description = description;
    }
}


