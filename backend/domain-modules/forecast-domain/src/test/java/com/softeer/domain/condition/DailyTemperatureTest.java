package com.softeer.domain.condition;

import com.softeer.domain.DailyTemperature;
import com.softeer.domain.DailyTemperatureFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyTemperatureTest {

    @DisplayName("dailyTemperatureRange 메서드는 최고 기온과 최저 기온의 차이를 올바르게 계산해야 한다.")
    @ParameterizedTest(name = "최고 기온 {0}°C, 최저 기온 {1}°C 일 때 일교차는 {2}°C 여야 한다.")
    @CsvSource({
            "25.0, 5.0, 20.0",   // 일반적인 양수 일교차
            "18.0, 12.0, 6.0",   // 중간 정도의 양수 일교차
            "10.0, 8.0, 2.0",    // 낮은 양수 일교차
            "15.0, 15.0, 0.0",   // 일교차가 0인 경우
            "5.0, 15.0, -10.0"   // 최고 기온이 최저 기온보다 낮은 비정상적인 경우
    })
    void dailyTemperatureRangeTest(double highestTemperature, double lowestTemperature, double expectedRange) {
        // Given
        DailyTemperature dailyTemperature = DailyTemperatureFixture.builder()
                .highestTemperature(highestTemperature)
                .lowestTemperature(lowestTemperature)
                .build();

        // When
        double actualRange = dailyTemperature.dailyTemperatureRange();

        // Then
        assertEquals(expectedRange, actualRange);
    }
}