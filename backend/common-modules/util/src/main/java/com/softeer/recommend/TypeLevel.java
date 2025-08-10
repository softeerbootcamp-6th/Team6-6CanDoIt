package com.softeer.recommend;

public enum TypeLevel {
    PRECIPITATION(1),
    SNOW_ACCUMULATION(2),
    WIND_SPEED(3),
    TEMPERATURE(4);

    private final int level;

    TypeLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
