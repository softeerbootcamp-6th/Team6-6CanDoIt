package com.softeer.domain.condition;

import com.softeer.entity.enums.PrecipitationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrecipitationConditionTest {

    @ParameterizedTest
    @EnumSource(PrecipitationType.class)
    @DisplayName("모든 PrecipitationType에 대해 올바른 형식의 설명 문자열을 반환해야 한다.")
    void displayDescription_shouldReturnCorrectStringForAllTypes(PrecipitationType type) {
        // Given
        String testPrecipitation = "30.0~50.0mm"; // 이 값은 description에 영향을 주지 않으므로 임의로 설정
        int testProbability = 10;
        int testSnowAccumulation = 1;

        PrecipitationCondition condition = new PrecipitationCondition(type, testPrecipitation, testProbability, testSnowAccumulation);

        // When
        String description = condition.displayDescription();

        // Then
        String expectedDescription;
        if (type == PrecipitationType.NONE) {
            expectedDescription = "0%";
        } else {
            expectedDescription = type.name() + " " + testProbability + ".0%";
        }

        assertEquals(expectedDescription, description);
    }
}