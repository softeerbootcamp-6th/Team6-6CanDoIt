package com.softeer.altitude.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureAltitudeAdjustmentTest {

    private final TemperatureAltitudeAdjustment transformer = new TemperatureAltitudeAdjustment();

    @ParameterizedTest
    @CsvSource({
            "15.0, 1000.0, 1000.0, 15.0",   // 고도 동일: 보정 없음
            "10.0, 1000.0, 1500.0, 13.25",    // 고도 하강: 온도 상승
            "20.0, 500.0, 1500.0, 26.5",     // 고도 하강(1000m): 온도 상승
            "0.0, 0.0, 1000.0, 6.5"            // 기준 고도가 0.0일 때
    })
    @DisplayName("고도 차이에 따른 온도가 올바르게 보정되어야 한다.")
    void adjust_shouldCorrectTemperatureBasedOnAltitudeDifference(
            double initialTemperature,
            double courseAltitude,
            double mountainAltitude,
            double expectedTemperature
    ) {
        // when
        double result = transformer.adjust(initialTemperature, courseAltitude, mountainAltitude);

        // then
        assertEquals(expectedTemperature, result, 0.001);
    }
}