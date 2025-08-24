package com.softeer.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeUtil {

    public static HikingTime getHikingTime(LocalDateTime dateTime, double duration) {
        long hours = (long) duration;
        long minutes = Math.round((duration - hours) * 60); // 소수 부분을 분으로 환산

        LocalDateTime arrivalTime = dateTime.plusHours(hours).plusMinutes(minutes);
        LocalDateTime descentTime = dateTime.plusHours(hours * 2).plusMinutes(minutes * 2);

        return new HikingTime(
                dateTime,
                arrivalTime,
                descentTime
        );
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

    public static Duration getRedisTtl(LocalDateTime targetTime) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (targetTime.isBefore(dateTime)) {
            return Duration.ZERO;
        }
        return Duration.between(dateTime, targetTime);
    }
}
