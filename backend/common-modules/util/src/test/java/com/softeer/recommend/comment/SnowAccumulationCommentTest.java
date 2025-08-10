package com.softeer.recommend.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnowAccumulationCommentTest {

    @Test
    @DisplayName("문자열 '적설없음'은 LEVEL_0을 반환해야 한다.")
    void testLevel0() {
        assertEquals(SnowAccumulationComment.LEVEL_0, SnowAccumulationComment.findsnowAccumulationComment("적설없음"));
    }

    @Test
    @DisplayName("문자열 '0.5cm 미만'은 LEVEL_1을 반환해야 한다.")
    void testLevel1() {
        assertEquals(SnowAccumulationComment.LEVEL_1, SnowAccumulationComment.findsnowAccumulationComment("0.5cm 미만"));
    }

    @Test
    @DisplayName("적설량 0.5~1cm 사이는 LEVEL_2를 반환해야 한다.")
    void testLevel2() {
        assertEquals(SnowAccumulationComment.LEVEL_2, SnowAccumulationComment.findsnowAccumulationComment("0.5cm"));
        assertEquals(SnowAccumulationComment.LEVEL_2, SnowAccumulationComment.findsnowAccumulationComment("0.7cm"));
        assertEquals(SnowAccumulationComment.LEVEL_2, SnowAccumulationComment.findsnowAccumulationComment("0.9cm"));
    }

    @Test
    @DisplayName("적설량 1.0~3.0cm 사이는 LEVEL_3을 반환해야 한다.")
    void testLevel3() {
        // 참고: CentimeterRange(1.0, 3.0)가 1.0을 포함하지 않는다면 이 테스트는 실패합니다.
        // 일반적으로 Range는 시작값을 포함하므로 이를 가정하고 테스트를 작성합니다.
        assertEquals(SnowAccumulationComment.LEVEL_3, SnowAccumulationComment.findsnowAccumulationComment("1.0cm"));
        assertEquals(SnowAccumulationComment.LEVEL_3, SnowAccumulationComment.findsnowAccumulationComment("2.0cm"));
        assertEquals(SnowAccumulationComment.LEVEL_3, SnowAccumulationComment.findsnowAccumulationComment("2.9cm"));
    }

    @Test
    @DisplayName("정의된 모든 범위를 벗어나면 LEVEL_4를 반환해야 한다.")
    void testLevel4() {
        assertEquals(SnowAccumulationComment.LEVEL_4, SnowAccumulationComment.findsnowAccumulationComment("3.0cm"));
        assertEquals(SnowAccumulationComment.LEVEL_4, SnowAccumulationComment.findsnowAccumulationComment("4.9cm"));
        assertEquals(SnowAccumulationComment.LEVEL_4, SnowAccumulationComment.findsnowAccumulationComment("4.9cm"));
        assertEquals(SnowAccumulationComment.LEVEL_4, SnowAccumulationComment.findsnowAccumulationComment("5.0cm 이상"));
    }
}