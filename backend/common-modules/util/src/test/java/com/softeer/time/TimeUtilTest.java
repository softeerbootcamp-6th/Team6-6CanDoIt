package com.softeer.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUtilTest {

    @Test
    @DisplayName("getBaseTime: 분·초·나노초를 00:00 으로 초기화한다")
    void getBaseTime_resetsToTopOfHour() {
        // given
        LocalDateTime given = LocalDateTime.of(2025, 8, 7, 14, 37, 18, 987_000_000);

        // when
        LocalDateTime base = TimeUtil.getBaseTime(given);

        // then
        assertEquals(LocalDateTime.of(2025, 8, 7, 14, 0), base);
    }

    @Nested
    @DisplayName("getHikingTime() 검증")
    class TimeUtilHikingTimeTest {

        @Test
        @DisplayName("2 시간 30 분 코스 → 같은 날 도착·하산 시각")
        void getHikingTime_sameDay() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 6, 15);
            LocalTime duration = LocalTime.of(2, 30);

            // when
            HikingTime result = TimeUtil.getHikingTime(start, duration);

            // then
            assertEquals(TimeUtil.getBaseTime(start), result.startTime());
            assertEquals(LocalDateTime.of(2025, 8, 7, 8, 0), result.arrivalTime());
            assertEquals(LocalDateTime.of(2025, 8, 7, 11, 0), result.descentTime());
        }

        @Test
        @DisplayName("3 시간 코스 → 자정 넘어 다음 날 도착·하산")
        void getClimbTime_crossMidnight() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 22, 45);
            LocalTime duration = LocalTime.of(3, 0);

            // when
            HikingTime result = TimeUtil.getHikingTime(start, duration);

            // then
            assertEquals(TimeUtil.getBaseTime(start), result.startTime());
            assertEquals(LocalDateTime.of(2025, 8, 8, 1, 0), result.arrivalTime());
            assertEquals(LocalDateTime.of(2025, 8, 8, 4, 0), result.descentTime());
        }
    }
}