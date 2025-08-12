package com.softeer.time;

import java.time.LocalTime;

public enum ShortTimePeriod {
    BEFORE_TWO(LocalTime.MIN, LocalTime.of(2, 10), 23),
    TWO(LocalTime.of(2,10), LocalTime.of(5,10), 2),
    FIVE(LocalTime.of(5, 10), LocalTime.of(8, 10), 5),
    EIGHT(LocalTime.of(8, 10), LocalTime.of(11, 10), 8),
    ELEVEN(LocalTime.of(11, 10), LocalTime.of(14, 10), 11),
    FOURTEEN(LocalTime.of(14, 10), LocalTime.of(17, 10), 14),
    SEVENTEEN(LocalTime.of(17, 10), LocalTime.of(20, 10), 17),
    TWENTY(LocalTime.of(20, 10), LocalTime.of(23, 10), 20),
    TWENTY_THREE(LocalTime.of(23, 10), LocalTime.MAX, 23);

    private final LocalTime minTime;
    private final LocalTime maxTime;
    private final int baseHour;

    ShortTimePeriod(LocalTime minTime, LocalTime maxTime, int baseHour) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.baseHour = baseHour;
    }

    private boolean contains(LocalTime time) {
        return (minTime.isBefore(time) || minTime.equals(time)) && maxTime.isAfter(time);
    }

    public static ShortTimePeriod get(LocalTime time) {
        for (ShortTimePeriod period : values()) {
            if (period.contains(time)) {
                return period;
            }
        }

        return TWENTY_THREE;
    }

    public int getBaseHour() {
        return baseHour;
    }
}
