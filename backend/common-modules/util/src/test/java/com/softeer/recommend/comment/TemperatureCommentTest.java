package com.softeer.recommend.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TemperatureCommentTest {

    @Test
    @DisplayName("기온 0~3도 상승 시 RISE_LEVEL_1을 반환해야 한다.")
    void testRiseLevel1() {
        assertEquals(TemperatureComment.RISE_LEVEL_1, TemperatureComment.findTemperatureComment(0));
        assertEquals(TemperatureComment.RISE_LEVEL_1, TemperatureComment.findTemperatureComment(1.5));
        assertEquals(TemperatureComment.RISE_LEVEL_1, TemperatureComment.findTemperatureComment(3));
    }

    @Test
    @DisplayName("기온 4~6도 상승 시 RISE_LEVEL_2를 반환해야 한다.")
    void testRiseLevel2() {
        assertEquals(TemperatureComment.RISE_LEVEL_2, TemperatureComment.findTemperatureComment(4));
        assertEquals(TemperatureComment.RISE_LEVEL_2, TemperatureComment.findTemperatureComment(5));
        assertEquals(TemperatureComment.RISE_LEVEL_2, TemperatureComment.findTemperatureComment(6));
    }

    @Test
    @DisplayName("기온 7~10도 상승 시 RISE_LEVEL_3을 반환해야 한다.")
    void testRiseLevel3() {
        assertEquals(TemperatureComment.RISE_LEVEL_3, TemperatureComment.findTemperatureComment(7));
        assertEquals(TemperatureComment.RISE_LEVEL_3, TemperatureComment.findTemperatureComment(8.5));
        assertEquals(TemperatureComment.RISE_LEVEL_3, TemperatureComment.findTemperatureComment(10));
    }

    @Test
    @DisplayName("기온 0~3도 하락 시 FALL_LEVEL_1을 반환해야 한다.")
    void testFallLevel1() {
        assertEquals(TemperatureComment.FALL_LEVEL_1, TemperatureComment.findTemperatureComment(-0.1));
        assertEquals(TemperatureComment.FALL_LEVEL_1, TemperatureComment.findTemperatureComment(-2));
        assertEquals(TemperatureComment.FALL_LEVEL_1, TemperatureComment.findTemperatureComment(-3));
    }

    @Test
    @DisplayName("기온 4~6도 하락 시 FALL_LEVEL_2를 반환해야 한다.")
    void testFallLevel2() {
        assertEquals(TemperatureComment.FALL_LEVEL_2, TemperatureComment.findTemperatureComment(-4));
        assertEquals(TemperatureComment.FALL_LEVEL_2, TemperatureComment.findTemperatureComment(-5));
        assertEquals(TemperatureComment.FALL_LEVEL_2, TemperatureComment.findTemperatureComment(-6));
    }

    @Test
    @DisplayName("기온 7~10도 하락 시 FALL_LEVEL_3을 반환해야 한다.")
    void testFallLevel3() {
        assertEquals(TemperatureComment.FALL_LEVEL_3, TemperatureComment.findTemperatureComment(-7));
        assertEquals(TemperatureComment.FALL_LEVEL_3, TemperatureComment.findTemperatureComment(-8.5));
        assertEquals(TemperatureComment.FALL_LEVEL_3, TemperatureComment.findTemperatureComment(-10));
    }

    @Test
    @DisplayName("정의된 모든 범위를 벗어나면 LEVEL_4를 반환해야 한다.")
    void testDefaultLevel4() {
        assertEquals(TemperatureComment.LEVEL_4, TemperatureComment.findTemperatureComment(11));
        assertEquals(TemperatureComment.LEVEL_4, TemperatureComment.findTemperatureComment(-10.1));
        assertEquals(TemperatureComment.LEVEL_4, TemperatureComment.findTemperatureComment(100));
        assertEquals(TemperatureComment.LEVEL_4, TemperatureComment.findTemperatureComment(-100));
    }
}