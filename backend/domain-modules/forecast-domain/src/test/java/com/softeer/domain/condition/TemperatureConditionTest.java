package com.softeer.domain.condition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemperatureConditionTest {

    @ParameterizedTest
    @ValueSource(doubles = {-100.0, 0.0, 10.0})
    @DisplayName("온도가 -100.0°C 이상 10.0°C 이하일 때 '매우 추워요'를 반환해야 한다.")
    void displayDescription_shouldReturnVeryCold_whenTemperatureIsInRange(double temperature) {
        // Given
        TemperatureCondition condition = new TemperatureCondition(temperature);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("매우 추워요", description);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.1, 15.0, 18.0})
    @DisplayName("온도가 10.1°C 이상 18.0°C 이하일 때 '추워요'를 반환해야 한다.")
    void displayDescription_shouldReturnCold_whenTemperatureIsInRange(double temperature) {
        // Given
        TemperatureCondition condition = new TemperatureCondition(temperature);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("추워요", description);
    }

    @ParameterizedTest
    @ValueSource(doubles = {18.1, 22.0, 25.0})
    @DisplayName("온도가 18.1°C 이상 25.0°C 이하일 때 '쾌적해요'를 반환해야 한다.")
    void displayDescription_shouldReturnPleasant_whenTemperatureIsInRange(double temperature) {
        // Given
        TemperatureCondition condition = new TemperatureCondition(temperature);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("쾌적해요", description);
    }

    @ParameterizedTest
    @ValueSource(doubles = {25.1, 28.0, 30.0})
    @DisplayName("온도가 25.1°C 이상 30.0°C 이하일 때 '더워요'를 반환해야 한다.")
    void displayDescription_shouldReturnHot_whenTemperatureIsInRange(double temperature) {
        // Given
        TemperatureCondition condition = new TemperatureCondition(temperature);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("더워요", description);
    }

    @ParameterizedTest
    @ValueSource(doubles = {30.1, 50.0, 100.0})
    @DisplayName("온도가 30.1°C 이상 100.0°C 이하일 때 '매우 더워요'를 반환해야 한다.")
    void displayDescription_shouldReturnVeryHot_whenTemperatureIsInRange(double temperature) {
        // Given
        TemperatureCondition condition = new TemperatureCondition(temperature);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("매우 더워요", description);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-101.0, 100.1, 150.0})
    @DisplayName("유효하지 않은 온도 값일 때 IllegalArgumentException을 던져야 한다.")
    void displayDescription_shouldThrowException_whenTemperatureIsInvalid(double temperature) {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new TemperatureCondition(temperature).displayDescription();
        });
    }
}