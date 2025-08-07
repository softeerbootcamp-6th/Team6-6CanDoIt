package com.softeer.activity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HikingActivityCalculatorExtensiveTest {

    @ParameterizedTest(name = "[{index}] T={0}, DTR={1}, P={2}, W={3}, H={4} → {5}")
    @CsvSource({
            // All lowest → CI=1.0000 → 나쁨
            "-10.0, 20.0, '50.0mm 이상', 6.0, 5.0, 나쁨",
            // All 2 → CI=2.0000 → 나쁨
            "0.0, 8.0, '1.3mm', 3.5, 20.0, 약간나쁨",
            // All 3 → CI=3.0000 → 약간나쁨
            "5.0, 8.0, '1.0mm', 4.0, 30.0, 약간나쁨",
            // All highest → CI=4.0000 → 매우 좋음
            "25.0, 5.0, '강수없음', 2.0, 45.0, 매우 좋음",

            // Edge between BAD and SLIGHTLY_BAD: CI just below 2.3939 → 나쁨
            "-6.0, 7.0, '1.3mm', 3.4, 40.0, 나쁨",
            // Edge between SLIGHTLY_BAD and GOOD: CI just above 3.1241 → 좋음
            "1.0, 16.2, '강수없음', 2.0, 50.0, 약간나쁨",
            // Mixed mid-range → SLIGHTLY_BAD
            "20.0, 2.0, '1.3mm', 5.0, 10.0, 약간나쁨",
            // One high, others low → GOOD
            "25.0, 20.0, '50.0mm 이상', 2.0, 50.0, 약간나쁨",
            // One low, others high → SLIGHTLY_BAD
            "-10.0, 5.0, '강수없음', 2.0, 45.0, 좋음",
            // Random combination → BAD
            "0.0, 9.3, '2.5mm', 5.0, 70.0, 나쁨",
            // Random combination → GOOD
            "15.0, 10.0, '1mm 미만', 3.0, 60.0, 좋음",
            // Random combination → 매우 좋음
            "22.0, 4.0, '강수없음', 1.0, 30.0, 매우 좋음"
    })
    void testCalculateHikingActivity_various(
            double temperature,
            double dailyRange,
            String precipitation,
            double windSpeed,
            double humidity,
            String expectedStatus
    ) {
        String actual = HikingActivityCalculator.calculateHikingActivity(
                temperature, dailyRange, precipitation, windSpeed, humidity
        );
        assertEquals(expectedStatus, actual);
    }
}
