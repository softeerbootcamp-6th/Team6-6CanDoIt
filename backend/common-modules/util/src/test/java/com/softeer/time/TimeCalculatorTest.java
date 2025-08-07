package com.softeer.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeCalculatorTest {

    private static final String BASE_DATE = "baseDate";
    private static final String BASE_TIME = "baseTime";

    @Test
    @DisplayName("ultraBaseTime() : 분(minute) ≥ 45 ➞ 같은 시(hour) HH30, 같은 날짜")
    void ultraBaseTime_after45() {
        // given
        LocalDateTime given = LocalDateTime.of(2025, 8, 7, 14, 45);

        // when
        Map<String, String> result = TimeCalculator.getUltraBaseTime(given);

        // then
        assertEquals("0807", result.get(BASE_DATE));
        assertEquals("1430", result.get(BASE_TIME));
    }

    @Test
    @DisplayName("ultraBaseTime() : 분(minute) < 45  ➞ 1 시간 이전 HH30, 같은 날짜")
    void ultraBaseTime_before45() {
        // given
        LocalDateTime given = LocalDateTime.of(2025, 8, 7, 14, 30);

        // when
        Map<String, String> result = TimeCalculator.getUltraBaseTime(given);

        // then
        assertEquals("0807", result.get(BASE_DATE));
        assertEquals("1330", result.get(BASE_TIME));
    }

    @Test
    @DisplayName("ultraBaseTime() : 00시 분(minute) < 45 ➞ 이전 날짜 2330")
    void ultraBaseTime_after2330() {
        LocalDateTime given = LocalDateTime.of(2025, 8, 7, 0, 30);
        Map<String, String> result = TimeCalculator.getUltraBaseTime(given);
        assertEquals("0806", result.get(BASE_DATE));
        assertEquals("2330", result.get(BASE_TIME));
    }

    @Test
    @DisplayName("shortBaseTime() : BEFORE_TWO 구간 ➞ 전날 날짜 & enum baseTime")
    void shortBaseTime_beforeTwo() {
        // given
        LocalDateTime given = LocalDateTime.of(2025, 8, 7, 1, 30);
        Map<String, String> result = TimeCalculator.getShortBaseTime(given);

        // when, then
        assertEquals("0806", result.get(BASE_DATE));
        assertEquals(ShortTimePeriod.BEFORE_TWO.getBaseTime(),
                result.get(BASE_TIME));
    }

    @Test
    @DisplayName("shortBaseTime() : 다른 구간 ➞ 날짜 유지 & enum baseTime")
    void shortBaseTime_other() {
        // given
        LocalDateTime given = LocalDateTime.of(2025, 8, 7, 6, 15);
        ShortTimePeriod period = ShortTimePeriod.get(given.toLocalTime());

        // when
        Map<String, String> result = TimeCalculator.getShortBaseTime(given);

        // then
        assertEquals("0807", result.get(BASE_DATE));
        assertEquals(period.getBaseTime(), result.get(BASE_TIME));
    }

    @Test
    @DisplayName("getClimbTime : 2.5h 코스 → 같은 날 예상·하산 시각")
    void climbTime_sameDay() {
        // given
        LocalDateTime start = LocalDateTime.of(2025, 8, 7, 6, 15);
        double hours = 2.5;

        // when
        Map<String, String> result = TimeCalculator.getClimbTime(start, hours);

        // then
        assertEquals("0807", result.get("expectedDate"));
        assertEquals("08:45", result.get("expectedTime"));

        assertEquals("0807", result.get("returnDate"));
        assertEquals("11:15", result.get("returnTime"));
    }

    @Test
    @DisplayName("getClimbTime : 3h 코스 → 자정 넘어 다음 날 도착")
    void climbTime_crossMidnight() {
        // given
        LocalDateTime start = LocalDateTime.of(2025, 8, 7, 22, 45);
        double hours = 3.0;     // 3h 00m

        // when
        Map<String, String> result = TimeCalculator.getClimbTime(start, hours);

        // then
        assertEquals("0808", result.get("expectedDate"));
        assertEquals("01:45", result.get("expectedTime"));

        assertEquals("0808", result.get("returnDate"));
        assertEquals("04:45", result.get("returnTime"));
    }
}