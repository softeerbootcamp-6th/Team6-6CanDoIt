package com.softeer.altitude.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class WindSpeedAltitudeAdjustmentTest {

    private final WindSpeedAltitudeAdjustment transformer = new WindSpeedAltitudeAdjustment();

    @ParameterizedTest
    @CsvSource({
            "10.0, 1000.0, 1000.0, 10.0",        // 고도가 동일할 때
            "10.0, 500.0, 1000.0, 8.408964",     // 코스 고도가 더 낮을 때
            "0.0, 500.0, 1000.0, 0.0"             // 기준 풍속이 0일 때
    })
    @DisplayName("고도 차이에 따른 풍속이 올바르게 보정되어야 한다.")
    void adjust_shouldCorrectWindSpeedBasedOnAltitudeDifference(
            double initialWindSpeed,
            double courseAltitude,
            double mountainAltitude,
            double expectedWindSpeed
    ) {
        // when
        double result = transformer.adjust(initialWindSpeed, courseAltitude, mountainAltitude);

        // then
        assertEquals(expectedWindSpeed, result, 0.001);
    }

}