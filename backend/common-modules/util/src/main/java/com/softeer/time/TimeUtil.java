package com.softeer.time;

import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeUtil {

    public static HikingTime getHikingTime(LocalDateTime dateTime, LocalTime duration) {
        LocalDateTime arrivalTime = dateTime.plusHours(duration.getHour()).plusMinutes(duration.getMinute());
        LocalDateTime descentTime = dateTime.plusHours(duration.getHour() * 2).plusMinutes(duration.getMinute() * 2);

        return new HikingTime(
                dateTime,
                arrivalTime,
                descentTime
        );
    }

    public static LocalDateTime getBaseTime(LocalDateTime dateTime) {
        return dateTime.withMinute(0).withSecond(0).withNano(0);
    }

    public static String getTimeString(LocalDateTime dateTime) {
        return String.format("%02d%02d",
                dateTime.getHour(),
                dateTime.getMinute());
    }

    public static String getDateString(LocalDateTime dateTime) {
        return String.format("%d%02d%02d",
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth());
    }
}
