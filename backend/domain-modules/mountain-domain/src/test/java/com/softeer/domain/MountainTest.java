package com.softeer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MountainTest {

    @Test
    @DisplayName("정상 파라미터로 생성 시 예외가 발생하지 않는다")
    void createValidMountain() {
        // given, when, then
        assertDoesNotThrow(MountainFixture::createDefault);
    }
}