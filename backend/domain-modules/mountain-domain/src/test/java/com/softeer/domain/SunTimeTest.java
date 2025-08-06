package com.softeer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class SunTimeTest {

    @Test
    @DisplayName("생성자: 정상 케이스에서는 예외가 발생하지 않는다")
    void createValidSunTime() {
        // given
        LocalTime sunrise = LocalTime.of(5, 30);
        LocalTime sunset = LocalTime.of(19, 30);

        // when, then
        assertDoesNotThrow(() -> new SunTime(sunrise, sunset));
    }
}