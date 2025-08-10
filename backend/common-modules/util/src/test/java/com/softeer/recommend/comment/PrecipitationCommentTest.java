package com.softeer.recommend.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrecipitationCommentTest {

    // 참고: findWindSpeedComment는 findPrecipitationComment의 오타로 보입니다.
    // 또한, 테스트를 위해 Condition, Literal, MillimeterRange 클래스가 구현되어 있어야 합니다.

    @Test
    @DisplayName("문자열 '강수없음'은 LEVEL_0을 반환해야 한다.")
    void testLevel0() {
        assertEquals(PrecipitationComment.LEVEL_0, PrecipitationComment.findPrecipitationComment("강수없음"));
    }

    @Test
    @DisplayName("문자열 '1mm 미만'은 LEVEL_1을 반환해야 한다.")
    void testLevel1() {
        assertEquals(PrecipitationComment.LEVEL_1, PrecipitationComment.findPrecipitationComment("1mm 미만"));
    }

    @Test
    @DisplayName("강수량 1.0~1.4mm 사이는 LEVEL_2를 반환해야 한다.")
    void testLevel2() {
        assertEquals(PrecipitationComment.LEVEL_2, PrecipitationComment.findPrecipitationComment("1.0mm"));
        assertEquals(PrecipitationComment.LEVEL_2, PrecipitationComment.findPrecipitationComment("1.2mm"));
        assertEquals(PrecipitationComment.LEVEL_2, PrecipitationComment.findPrecipitationComment("1.4mm"));
    }

    @Test
    @DisplayName("강수량 1.5~2.9mm 사이는 LEVEL_3을 반환해야 한다.")
    void testLevel3() {
        assertEquals(PrecipitationComment.LEVEL_3, PrecipitationComment.findPrecipitationComment("1.5mm"));
        assertEquals(PrecipitationComment.LEVEL_3, PrecipitationComment.findPrecipitationComment("2.0mm"));
        assertEquals(PrecipitationComment.LEVEL_3, PrecipitationComment.findPrecipitationComment("2.9mm"));
    }

    @Test
    @DisplayName("정의된 모든 범위를 벗어나면 LEVEL_4를 반환해야 한다.")
    void testLevel4() {
        assertEquals(PrecipitationComment.LEVEL_4, PrecipitationComment.findPrecipitationComment("3.0mm"));
        assertEquals(PrecipitationComment.LEVEL_4, PrecipitationComment.findPrecipitationComment("30.0~50.0mm"));
        assertEquals(PrecipitationComment.LEVEL_4, PrecipitationComment.findPrecipitationComment("50.0mm 이상"));
    }
}