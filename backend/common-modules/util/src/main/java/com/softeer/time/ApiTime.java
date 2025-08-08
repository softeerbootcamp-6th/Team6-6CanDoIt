package com.softeer.time;

import java.time.LocalDateTime;

public record ApiTime(
        String baseDate,
        String baseTime
) {

    public ApiTime(
            LocalDateTime dateTime
    ) {
        this(
            TimeUtil.getDateString(dateTime),
            TimeUtil.getTimeString(dateTime)
        );
    }
}
