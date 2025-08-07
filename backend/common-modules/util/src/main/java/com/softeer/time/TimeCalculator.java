package com.softeer.time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public final class TimeCalculator {

    private static final int referenceMinute = 45;
    private static final String BASE_DATE = "baseDate";
    private static final String BASE_TIME = "baseTime";
    private static final String EXPECTED_DATE = "expectedDate";
    private static final String EXPECTED_TIME = "expectedTime";
    private static final String RETURN_DATE = "returnDate";
    private static final String RETURN_TIME = "returnTime";

    public static Map<String, String> getUltraBaseTime(LocalDateTime dateTime) {
        Map<String, String> map = new HashMap<>();

        if (dateTime.getMinute() < referenceMinute) {
            dateTime = dateTime.minusHours(1);
        }

        map.put(BASE_DATE, toDateString(dateTime));
        map.put(BASE_TIME, String.format("%02d30",  dateTime.getHour()));

        return map;
    }

    public static Map<String, String> getShortBaseTime(LocalDateTime dateTime) {
        Map<String, String> map = new HashMap<>();

        ShortTimePeriod shortTimePeriod = ShortTimePeriod.get(dateTime.toLocalTime());

        if (shortTimePeriod == ShortTimePeriod.BEFORE_TWO) {
            dateTime = dateTime.minusDays(1);
        }

        map.put(BASE_DATE, toDateString(dateTime));
        map.put(BASE_TIME, shortTimePeriod.getBaseTime());

        return map;
    }

    public static Map<String, String> getClimbTime(LocalDateTime dateTime, double duration) {
        Map<String, String> map = new HashMap<>();
        LocalTime durationTime = toTime(duration);

        LocalDateTime expectedDateTime = dateTime.plusHours(durationTime.getHour()).plusMinutes(durationTime.getMinute());
        map.put(EXPECTED_DATE, toDateString(expectedDateTime));
        map.put(EXPECTED_TIME, toTimeString(expectedDateTime));

        LocalDateTime returnDateTime = dateTime.plusHours(durationTime.getHour() * 2).plusMinutes(durationTime.getMinute() * 2);
        map.put(RETURN_DATE, toDateString(returnDateTime));
        map.put(RETURN_TIME, toTimeString(returnDateTime));

        return map;
    }

    private static String toDateString(LocalDateTime dateTime) {
        int month = dateTime.getMonth().getValue();
        int day = dateTime.getDayOfMonth();

        return String.format("%02d%02d", month, day);
    }

    private static String toTimeString(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();

        return String.format("%02d:%02d", hour, minute);
    }

    private static LocalTime toTime(double duration) {
        int hour = (int) duration;
        int minute = (int) ((duration - hour) * 60);

        return LocalTime.of(hour, minute);
    }
}
