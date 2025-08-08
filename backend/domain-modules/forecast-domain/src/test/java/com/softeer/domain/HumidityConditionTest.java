package com.softeer.domain;

import com.softeer.domain.condition.HumidityCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HumidityConditionTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 20, 40})
    @DisplayName("습도가 0% 이상 40% 이하일 때 '건조해요'를 반환해야 한다.")
    void displayDescription_shouldReturnDry_whenHumidityIsInRange(int humidity) {
        // Given
        HumidityCondition condition = new HumidityCondition(humidity);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("건조해요", description);
    }

    @ParameterizedTest
    @ValueSource(ints = {41, 55, 60})
    @DisplayName("습도가 41% 이상 60% 이하일 때 '쾌적해요'를 반환해야 한다.")
    void displayDescription_shouldReturnPleasant_whenHumidityIsInRange(int humidity) {
        // Given
        HumidityCondition condition = new HumidityCondition(humidity);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("쾌적해요", description);
    }

    @ParameterizedTest
    @ValueSource(ints = {61, 70, 80})
    @DisplayName("습도가 61% 이상 80% 이하일 때 '습해요'를 반환해야 한다.")
    void displayDescription_shouldReturnHumid_whenHumidityIsInRange(int humidity) {
        // Given
        HumidityCondition condition = new HumidityCondition(humidity);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("습해요", description);
    }

    @ParameterizedTest
    @ValueSource(ints = {81, 90, 100})
    @DisplayName("습도가 81% 이상 100% 이하일 때 '매우 습해요'를 반환해야 한다.")
    void displayDescription_shouldReturnVeryHumid_whenHumidityIsInRange(int humidity) {
        // Given
        HumidityCondition condition = new HumidityCondition(humidity);

        // When
        String description = condition.displayDescription();

        // Then
        assertEquals("매우 습해요", description);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 101, 200})
    @DisplayName("유효하지 않은 습도 값일 때 IllegalArgumentException을 던져야 한다.")
    void displayDescription_shouldThrowException_whenHumidityIsInvalid(int humidity) {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new HumidityCondition(humidity).displayDescription();
        });
    }
}