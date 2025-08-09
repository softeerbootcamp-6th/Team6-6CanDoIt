package com.softeer.time;

import java.time.LocalDateTime;

import static com.softeer.time.TimeUtil.getBaseTime;

public class ApiTimeUtil {

    private static final int REFERENCE_MINUTE = 45;

    public static LocalDateTime getBatchAlignedUltraBaseTime(LocalDateTime dateTime) {
        int minute = dateTime.getMinute();
        int minuteFloored = (minute / 10) * 10;

        return getBaseTime(dateTime).withMinute(minuteFloored);
    }

    public static ApiTime getUltraBaseTime(LocalDateTime dateTime) {
        if (dateTime.getMinute() < REFERENCE_MINUTE) {
            dateTime = dateTime.minusHours(1);
        }

        LocalDateTime apiTime = getBaseTime(dateTime).withMinute(30);

        return new ApiTime(apiTime);
    }

    public static ApiTime getShortBaseTime(LocalDateTime dateTime) {
        ShortTimePeriod shortTimePeriod = ShortTimePeriod.get(dateTime.toLocalTime());

        if (shortTimePeriod == ShortTimePeriod.BEFORE_TWO) {
            dateTime = dateTime.minusDays(1);
        }

        LocalDateTime baseTime = getBaseTime(dateTime.withHour(shortTimePeriod.getBaseHour()));

        return new ApiTime(baseTime);
    }

    public static String getMountainBaseTime(LocalDateTime dateTime) {
        return String.format("%d%02d%02d%02d%02d",
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute());
    }
}
