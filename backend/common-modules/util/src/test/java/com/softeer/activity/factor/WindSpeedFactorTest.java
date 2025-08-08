package com.softeer.activity.factor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class WindSpeedFactorTest {

    @ParameterizedTest(name = "풍속 {0}m/s → 점수 {1}")
    @CsvSource({
            "0.0, 4",
            "3.3, 4",
            "3.4, 3",
            "4.9, 3",
            "5.0, 1",
            "10.0, 1"
    })
    void testWind(double wind, int expected) {
        int actual = WindSpeedFactor.calculateFactor(wind);
        assertEquals(expected, actual);
    }

}