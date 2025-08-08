package com.softeer.domain.condition;

import com.softeer.entity.enums.Sky;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkyConditionTest {

    @ParameterizedTest
    @EnumSource(Sky.class)
    @DisplayName("모든 Sky 타입에 대해 올바른 설명 문자열을 반환해야 한다.")
    void displayDescription_shouldReturnCorrectDescriptionForAllSkyTypes(Sky sky) {
        // Given
        SkyCondition condition = new SkyCondition(sky);

        // When
        String description = condition.displayDescription();

        // Then
        String expectedDescription = sky.getDisplayDescription();
        assertEquals(expectedDescription, description);
    }
}