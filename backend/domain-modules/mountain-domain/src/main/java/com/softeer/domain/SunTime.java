package com.softeer.domain;

import java.time.LocalTime;

public record SunTime(
        LocalTime sunrise,
        LocalTime sunset
) {
}
