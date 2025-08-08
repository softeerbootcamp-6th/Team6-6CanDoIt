package com.softeer.activity.factor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DailyTemperatureRangeFactorTest {

    @ParameterizedTest(name = "일교차 {0}℃ → 점수 {1}")
    @CsvSource({
            "0.0, 4",
            "6.9, 4",
            "7.0, 3",
            "9.2, 3",
            "9.3, 2",
            "16.1, 2",
            "16.2, 1",
            "20.0, 1"
    })
    void testDailyRange(double range, int expected) {
        int actual = DailyTemperatureRangeFactor.calculateFactor(range);
        assertEquals(expected, actual);
    }
}