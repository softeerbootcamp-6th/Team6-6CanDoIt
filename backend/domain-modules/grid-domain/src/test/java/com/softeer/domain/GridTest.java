package com.softeer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridTest {

    @Test
    @DisplayName("Fixture Builder를 통해 Grid 객체를 생성하면 id, x, y 좌표가 올바르게 설정되어야 한다.")
    void builder_shouldCreateCorrectObject_whenAllValuesAreProvided() {
        int expectedId = 1;
        int expectedX = 50;
        int expectedY = 100;

        Grid grid = GridFixture.builder()
                .id(expectedId)
                .x(expectedX)
                .y(expectedY)
                .build();

        assertEquals(expectedId, grid.id());
        assertEquals(expectedX, grid.x());
        assertEquals(expectedY, grid.y());
    }

    @Test
    @DisplayName("Fixture Builder를 통해 x() 메서드는 올바른 x 좌표를 반환해야 한다.")
    void builder_xMethod_shouldReturnCorrectValue() {
        // Given
        int expectedX = 50;
        Grid grid = GridFixture.builder()
                .x(expectedX)
                .y(100) // y는 기본값과 다른 임의의 값으로 설정
                .build();

        // When
        int actualX = grid.x();

        // Then
        assertEquals(expectedX, actualX);
    }

    @Test
    @DisplayName("Fixture Builder를 통해 y() 메서드는 올바른 y 좌표를 반환해야 한다.")
    void builder_yMethod_shouldReturnCorrectValue() {
        // Given
        int expectedY = 100;
        Grid grid = GridFixture.builder()
                .x(50) // x는 기본값과 다른 임의의 값으로 설정
                .y(expectedY)
                .build();

        // When
        int actualY = grid.y();

        // Then
        assertEquals(expectedY, actualY);
    }
}