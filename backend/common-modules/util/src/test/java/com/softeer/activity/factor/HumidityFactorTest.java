package com.softeer.activity.factor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HumidityFactorTest {

    @ParameterizedTest(name = "{0}% → {1}")
    @CsvSource({
            "0.1, 1",
            "10.0, 1",
            "10.1, 3",
            "30.0, 3",
            "40.0, 3",
            "40.1, 4",
            "60.0, 4",
            "70.0, 4",
            "70.1, 1",
            "100.0, 1"
    })
    void testCalculateFactor(double hum, int expected) {
        int actual = HumidityFactor.calculateFactor(hum);
        assertEquals(expected, actual);
    }

}