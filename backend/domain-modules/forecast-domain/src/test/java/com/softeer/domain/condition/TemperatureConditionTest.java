package com.softeer.domain.condition;

import com.softeer.apparent.ApparentTemperatureCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureConditionTest {

    @Test
    @DisplayName("계산 위임: (temperature, windSpeed, humidity) 생성자가 실제 체감온도를 정확히 채운다")
    void apparentTemperatureDelegation() {
        double temperature = 5.0;       // °C
        double windSpeed = 4.0;         // m/s
        double humidity = 80.0;         // %

        double expected = ApparentTemperatureCalculator.calculateApparentTemperature(temperature, windSpeed, humidity);

        TemperatureCondition condition = new TemperatureCondition(temperature, windSpeed, humidity);

        assertEquals(expected, condition.apparentTemperature());
    }

    @ParameterizedTest(name = "[{index}] apparent={0}°C → \"{1}\"")
    @DisplayName("설명 매핑: 체감온도 구간(경계 포함/배제) 검증")
    @CsvSource({
            // VERY_COLD: [-100.0, 10.0]
            "-15.0, 매우 추워요",
            "10.0, 매우 추워요",

            // COLD: [10.1, 18.0]
            "10.1, 추워요",
            "18.0, 추워요",

            // PLEASANT: [18.1, 25.0]
            "18.1, 쾌적해요",
            "25.0, 쾌적해요",

            // HOT: [25.1, 30.0]
            "25.1, 더워요",
            "30.0, 더워요",

            // VERY_HOT: [30.1, 100.0]
            "30.1, 매우 더워요",
            "45.0, 매우 더워요"
    })
    void descriptionMappingByRange(double apparent, String expectedDescription) {
        // temperature 필드는 로직에 영향 없고, displayDescription은 apparentTemperature만 사용
        TemperatureCondition condition = new TemperatureCondition(0.0, apparent);
        assertEquals(expectedDescription, condition.displayDescription());
    }

    @Test
    @DisplayName("예외: 체감온도가 정의된 구간 밖이면 IllegalArgumentException")
    void descriptionOutOfRangeThrows() {
        TemperatureCondition condition = new TemperatureCondition(0.0, 1000.0);
        assertThrows(IllegalArgumentException.class, condition::displayDescription);
    }
}
