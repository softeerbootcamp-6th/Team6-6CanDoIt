package com.softeer.domain;

import com.softeer.domain.condition.WindCondition;
import com.softeer.entity.enums.WindDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WindConditionTest {

    @ParameterizedTest
    @CsvSource({
            "N, 2.0, 약한 북풍",
            "E, 5.0, 약간 강한 동풍",
            "W, 10.0, 강한 서풍",
            "NW, 0.0, 약한 북서풍",
            "SE, 8.99, 약간 강한 남동풍",
            "NNW, 9.0, 강한 북북서풍"
    })
    @DisplayName("풍속과 풍향에 따라 올바른 설명 문자열을 반환해야 한다.")
    void displayDescription_shouldReturnCorrectFormat(WindDirection direction, double windSpeed, String expectedDescription) {
        // Given
        WindCondition condition = new WindCondition(direction, windSpeed);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals(expectedDescription, description);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0})
    @DisplayName("유효하지 않은 풍속 값일 때 IllegalArgumentException을 던져야 한다.")
    void displayDescription_shouldThrowException_whenWindSpeedIsInvalid(double windSpeed) {
        // Given & When & Then
        WindDirection direction = WindDirection.N; // 풍향은 테스트에 영향을 주지 않으므로 임의로 설정
        assertThrows(IllegalArgumentException.class, () -> {
            new WindCondition(direction, windSpeed).displayDescription();
        });
    }
}