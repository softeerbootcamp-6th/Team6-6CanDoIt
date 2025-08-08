package com.softeer.time;

import java.time.LocalDateTime;

public record HikingTime(
        LocalDateTime startTime,
        LocalDateTime arrivalTime,
        LocalDateTime descentTime
) {

    public HikingTime {
        startTime = TimeUtil.getBaseTime(startTime);
        arrivalTime = TimeUtil.getBaseTime(arrivalTime);
        descentTime = TimeUtil.getBaseTime(descentTime);
    }
}
