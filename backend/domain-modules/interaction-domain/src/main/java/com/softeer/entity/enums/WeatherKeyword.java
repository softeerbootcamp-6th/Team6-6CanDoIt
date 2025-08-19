package com.softeer.entity.enums;

public enum WeatherKeyword implements KeywordInterface {
    SUNNY("화창해요"),
    CLOUDY("구름이 많아요"),
    HOT("더워요"),
    COLD("추워요");

    private final String description;

    WeatherKeyword(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
