package com.softeer.time;

import java.time.LocalTime;

public enum ShortTimePeriod {
    BEFORE_TWO(LocalTime.of(0, 0), LocalTime.of(2, 10), "2300"),
    TWO(LocalTime.of(2,10), LocalTime.of(5,10), "0200"),
    FIVE(LocalTime.of(5, 10), LocalTime.of(8, 10), "0500"),
    EIGHT(LocalTime.of(8, 10), LocalTime.of(11, 10), "0800"),
    ELEVEN(LocalTime.of(11, 10), LocalTime.of(14, 10), "1100"),
    FOURTEEN(LocalTime.of(14, 10), LocalTime.of(17, 10), "1400"),
    SEVENTEEN(LocalTime.of(17, 10), LocalTime.of(20, 10), "1700"),
    TWENTY(LocalTime.of(20, 10), LocalTime.of(23, 10), "2000"),
    TWENTY_THREE(LocalTime.of(23, 10), LocalTime.of(23, 59), "2300");

    private final LocalTime minTime;
    private final LocalTime maxTime;
    private final String baseTime;

    ShortTimePeriod(LocalTime minTime, LocalTime maxTime, String baseTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.baseTime = baseTime;
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

    public String getBaseTime() {
        return baseTime;
    }
}
