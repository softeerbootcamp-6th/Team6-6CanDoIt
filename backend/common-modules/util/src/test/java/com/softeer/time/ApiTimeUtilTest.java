package com.softeer.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiTimeUtilTest {

    @Nested
    @DisplayName("getBatchAlignedUltraBaseTime() 검증")
    class BatchAlignedUltraBaseTime {

        @Test
        @DisplayName("분을 10분 단위로 내림(floor) 정렬한다 (예: 14:37 → 14:30)")
        void batchAlignedUltraBaseTime_floorToTenMinutes() {
            // given
            LocalDateTime given = LocalDateTime.of(2025, 8, 7, 14, 37, 45, 123);

            // when
            LocalDateTime dateTime = ApiTimeUtil.getBatchAlignedUltraBaseTime(given);

            // then
            assertEquals(LocalDateTime.of(2025, 8, 7, 14, 30), dateTime);
        }

        @Test
        @DisplayName("분이 정확히 배수일 때 그대로 유지 (예: 14:20 → 14:20)")
        void batchAlignedUltraBaseTime_exactMultiple() {
            // given
            LocalDateTime given = LocalDateTime.of(2025, 8, 7, 14, 20, 5, 0);

            // when
            LocalDateTime dateTime = ApiTimeUtil.getBatchAlignedUltraBaseTime(given);

            // then
            assertEquals(LocalDateTime.of(2025, 8, 7, 14, 20), dateTime);
        }

        @Test
        @DisplayName("0-9분 구간은 00분으로 (예: 14:07 → 14:00)")
        void batchAlignedUltraBaseTime_firstDecile() {
            // given
            LocalDateTime given = LocalDateTime.of(2025, 8, 7, 14, 7, 0, 0);

            // when
            LocalDateTime dateTime = ApiTimeUtil.getBatchAlignedUltraBaseTime(given);

            // then
            assertEquals(LocalDateTime.of(2025, 8, 7, 14, 0), dateTime);
        }
    }

    @Nested
    @DisplayName("getUltraBaseTime() 검증")
    class UltraBaseTime {

        @Test
        @DisplayName("분 < 45 ➞ 1 시간 전 HH:30")
        void minuteBelowReference() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 14, 30);

            // when
            ApiTime apiTime = ApiTimeUtil.getUltraBaseTime(start);

            // then
            assertEquals("20250807", apiTime.baseDate());
            assertEquals("1330", apiTime.baseTime());
        }

        @Test
        @DisplayName("분 < 45 ➞ 하루 전 23:30")
        void minuteBelowReference_returnPreviousDay() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 00, 30);

            // when
            ApiTime apiTime = ApiTimeUtil.getUltraBaseTime(start);

            // then
            assertEquals("20250806", apiTime.baseDate());
            assertEquals("2330", apiTime.baseTime());
        }

        @Test
        @DisplayName("분 ≥ 45 ➞ 같은 시(HH) 30분")
        void minuteAboveOrEqualReference() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 14, 46);

            // when
            ApiTime apiTime = ApiTimeUtil.getUltraBaseTime(start);

            // then
            assertEquals("20250807", apiTime.baseDate());
            assertEquals("1430", apiTime.baseTime());
        }
    }

    @Nested
    @DisplayName("getShortBaseTime() 검증")
    class ShortBaseTime {

        @Test
        @DisplayName("BEFORE_TWO 구간 ➞ 하루 전 & 23:00")
        void beforeTwoPeriod() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 1, 30);

            // when
            ApiTime apiTime = ApiTimeUtil.getShortBaseTime(start);

            // then
            assertEquals("20250806", apiTime.baseDate());
            assertEquals("2300", apiTime.baseTime());
        }

        @Test
        @DisplayName("다른 구간 ➞ 같은 날 & 구간 baseTime")
        void otherPeriod() {
            // given
            LocalDateTime start = LocalDateTime.of(2025, 8, 7, 6, 15);

            ShortTimePeriod period = ShortTimePeriod.get(start.toLocalTime());

            // when
            ApiTime apiTime = ApiTimeUtil.getShortBaseTime(start);

            // then
            assertEquals("20250807", apiTime.baseDate());
            assertEquals("0500", apiTime.baseTime());
        }
    }

    @Test
    @DisplayName("getMountainBaseTime: YYYYMMDDHHmm 형식으로 포맷된다")
    void getMountainBaseTime_success() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 7, 6, 15);

        // when, then
        String expected = "202508070615";
        assertEquals(expected, ApiTimeUtil.getMountainBaseTime(dateTime));
    }
}