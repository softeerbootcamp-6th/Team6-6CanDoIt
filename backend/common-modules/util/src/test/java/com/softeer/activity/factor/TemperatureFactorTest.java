package com.softeer.activity.factor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureFactorTest {

    @ParameterizedTest(name = "온도 {0}℃ → 점수 {1}")
    @CsvSource({
            "-10.0, 1",
            "-6.0, 1",
            "-5.9, 2",
            "0.0, 2",
            "4.0, 2",
            "4.1, 3",
            "14.0, 3",
            "14.1, 4",
            "26.0, 4",
            "26.1, 1",
            "100.0, 1"
    })
    void testTemperature(double temp, int expected) {
        int actual = TemperatureFactor.calculateFactor(temp);
        assertEquals(expected, actual);
    }
}