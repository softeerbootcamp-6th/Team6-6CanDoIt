package com.softeer.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeCalculatorTest {

    @Nested
    @DisplayName("getUltraBaseTime() 검증")
    class UltraBaseTime {

        @Test
        @DisplayName("분 < 45 ➞ 1 시간 전 HH:30")
        void minuteBelowReference() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 14, 30);

            // when
            LocalDateTime base = TimeCalculator.getUltraBaseTime(start);

            // then
            assertEquals(LocalDate.of(2025, 8, 7), base.toLocalDate());
            assertEquals(LocalTime.of(13, 30), base.toLocalTime());
        }

        @Test
        @DisplayName("분 < 45 ➞ 하루 전 23:30")
        void minuteBelowReference_returnPreviousDay() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 00, 30);

            // when
            LocalDateTime base = TimeCalculator.getUltraBaseTime(start);

            // then
            assertEquals(LocalDate.of(2025, 8, 6), base.toLocalDate());
            assertEquals(LocalTime.of(23, 30), base.toLocalTime());
        }

        @Test
        @DisplayName("분 ≥ 45 ➞ 같은 시(HH) 30분")
        void minuteAboveOrEqualReference() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 14, 46);

            // when
            LocalDateTime base = TimeCalculator.getUltraBaseTime(start);

            // then
            assertEquals(LocalDate.of(2025, 8, 7), base.toLocalDate());
            assertEquals(LocalTime.of(14, 30), base.toLocalTime());
        }
    }

    @Nested
    @DisplayName("getShortBaseTime() 검증")
    class ShortBaseTime {

        @Test
        @DisplayName("BEFORE_TWO 구간 ➞ 하루 전 & 23:00")
        void beforeTwoPeriod() {
            // given 2025-08-07 01:30
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 1, 30);

            // when
            LocalDateTime base = TimeCalculator.getShortBaseTime(start);

            // then (전날 02:00)
            assertEquals(LocalDate.of(2025, 8, 6), base.toLocalDate());
            assertEquals(LocalTime.of(23, 0), base.toLocalTime());
        }

        @Test
        @DisplayName("다른 구간 ➞ 같은 날 & 구간 baseTime")
        void otherPeriod() {
            // given 2025-08-07 06:15  → 예: MORNING(06) 구간
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 6, 15);

            ShortTimePeriod period = ShortTimePeriod.get(start.toLocalTime());

            // when
            LocalDateTime base = TimeCalculator.getShortBaseTime(start);

            // then (같은 날, period의 baseTime:00)
            assertEquals(LocalDate.of(2025, 8, 7), base.toLocalDate());
            assertEquals(LocalTime.of(period.getBaseHour(), 0), base.toLocalTime());
        }
    }

    @Nested
    @DisplayName("getHikingTime() 검증")
    class TimeCalculatorHikingTimeTest {

        @Test
        @DisplayName("2 시간 30 분 코스 → 같은 날 도착·하산 시각")
        void getHikingTime_sameDay() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 6, 15);
            LocalTime duration = LocalTime.of(2, 30);

            // when
            HikingTime result = TimeCalculator.getHikingTime(start, duration);

            // then
            assertEquals(start, result.startTime());
            assertEquals(LocalDateTime.of(2025, 8, 7, 8, 45), result.arrivalTime());
            assertEquals(LocalDateTime.of(2025, 8, 7, 11, 15), result.descentTime());
        }

        @Test
        @DisplayName("3 시간 코스 → 자정 넘어 다음 날 도착·하산")
        void getClimbTime_crossMidnight() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 22, 45);
            LocalTime duration = LocalTime.of(3, 0);

            // when
            HikingTime result = TimeCalculator.getHikingTime(start, duration);

            // then
            assertEquals(start, result.startTime());
            assertEquals(LocalDateTime.of(2025, 8, 8, 1, 45), result.arrivalTime());
            assertEquals(LocalDateTime.of(2025, 8, 8, 4, 45), result.descentTime());
        }
    }
}