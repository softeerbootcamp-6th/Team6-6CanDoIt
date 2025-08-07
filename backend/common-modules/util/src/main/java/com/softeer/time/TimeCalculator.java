package com.softeer.time;

import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeCalculator {

    private static final int referenceMinute = 45;

    public static LocalDateTime getUltraBaseTime(LocalDateTime dateTime) {
        if (dateTime.getMinute() < referenceMinute) {
            dateTime = dateTime.minusHours(1);
        }

        return dateTime.withMinute(30);
    }

    public static LocalDateTime getShortBaseTime(LocalDateTime dateTime) {
        ShortTimePeriod shortTimePeriod = ShortTimePeriod.get(dateTime.toLocalTime());

        if (shortTimePeriod == ShortTimePeriod.BEFORE_TWO) {
            dateTime = dateTime.minusDays(1);
        }

        return dateTime.withHour(shortTimePeriod.getBaseHour()).withMinute(0);
    }

    public static HikingTime getHikingTime(LocalDateTime dateTime, LocalTime duration) {
        LocalDateTime arrivalTime = dateTime.plusHours(duration.getHour()).plusMinutes(duration.getMinute());
        LocalDateTime descentTime = dateTime.plusHours(duration.getHour() * 2).plusMinutes(duration.getMinute() * 2);

        return new HikingTime(
                dateTime,
                arrivalTime,
                descentTime
        );
    }
}
