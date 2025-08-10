package com.softeer.recommend;

public enum MeasurementLevel {
    LEVEL_ZERO(0),
    LEVEL_ONE(1),
    LEVEL_TWO(2),
    LEVEL_THREE(3),
    LEVEL_FOUR(4);

    private final int level;

    MeasurementLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
