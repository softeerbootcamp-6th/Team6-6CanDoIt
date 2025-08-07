package com.softeer.time;

import java.time.LocalDateTime;

public record HikingTime(
        LocalDateTime startTime,
        LocalDateTime arrivalTime,
        LocalDateTime descentTime
) {
}
