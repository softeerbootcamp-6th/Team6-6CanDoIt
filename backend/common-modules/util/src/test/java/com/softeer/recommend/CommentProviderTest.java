package com.softeer.recommend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommentProviderTest {

    @Test
    void testLevel4Priority() {
        // LEVEL_4가 가장 높은 우선순위를 가지는 경우
        String result = CommentProvider.provideComment(
                "3mm 이상",     // PRECIPITATION LEVEL_4
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                2.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("오늘은 등산을 강력히 자제해 주세요.", result);
    }

    @Test
    void testLevel3Priority() {
        // LEVEL_3이 가장 높은 우선순위를 가지는 경우
        String result = CommentProvider.provideComment(
                "1mm 미만",     // PRECIPITATION LEVEL_1
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                8.0,           // WIND_SPEED LEVEL_3
                2.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("바람이 강하게 부니 등산 시 유의해주세요.", result);
    }

    @Test
    void testLevel3SnowAccumulation() {
        // 적설량 LEVEL_3
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "2.0cm",       // SNOW_ACCUMULATION LEVEL_3
                1.0,           // WIND_SPEED LEVEL_1
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("오늘은 등산을 추천드리지 않아요.", result);
    }

    @Test
    void testLevel3Temperature() {
        // 온도 LEVEL_3 (상승)
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                8.0            // TEMPERATURE LEVEL_3 (상승)
        );
        assertEquals("더위에 주의하세요.", result);
    }

    @Test
    void testLevel3TemperatureFall() {
        // 온도 LEVEL_3 (하락)
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                -8.0           // TEMPERATURE LEVEL_3 (하락)
        );
        assertEquals("한파에 주의하세요.", result);
    }

    @Test
    void testLevel2Priority() {
        // LEVEL_2가 가장 높은 우선순위를 가지는 경우
        String result = CommentProvider.provideComment(
                "1.2mm",       // PRECIPITATION LEVEL_2
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("바람막이를 챙겨가세요.", result);
    }

    @Test
    void testLevel2WindSpeed() {
        // 풍속 LEVEL_2
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                4.0,           // WIND_SPEED LEVEL_2
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("바람막이를 챙겨가세요.", result);
    }

    @Test
    void testLevel2SnowAccumulation() {
        // 적설량 LEVEL_2
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "0.7cm",       // SNOW_ACCUMULATION LEVEL_2
                1.0,           // WIND_SPEED LEVEL_1
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("눈이 쌓일 수 있어요.", result);
    }

    @Test
    void testLevel2Temperature() {
        // 온도 LEVEL_2 (상승)
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                5.0            // TEMPERATURE LEVEL_2 (상승)
        );
        assertEquals("여러 겹의 옷을 입고 가시는 것을 추천드려요.", result);
    }

    @Test
    void testLevel2TemperatureFall() {
        // 온도 LEVEL_2 (하락)
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                -5.0           // TEMPERATURE LEVEL_2 (하락)
        );
        assertEquals("여러 겹의 옷을 입고 가시는 것을 추천드려요.", result);
    }

    @Test
    void testLevel1Priority() {
        // 모든 조건이 LEVEL_1인 경우
        String result = CommentProvider.provideComment(
                "1mm 미만",     // PRECIPITATION LEVEL_1
                "0.5cm 미만",       // SNOW_ACCUMULATION LEVEL_1
                1.0,           // WIND_SPEED LEVEL_1
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("등산하기 좋은 날씨에요.", result);
    }

    @Test
    void testLevel0Priority() {
        // LEVEL_0과 LEVEL_1 혼재 시 LEVEL_1 우선
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                1.0,           // WIND_SPEED LEVEL_1
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("등산하기 좋은 날씨에요.", result);
    }

    @Test
    void testSameLevelDifferentTypes() {
        // 같은 레벨일 때 타입별 우선순위 테스트
        // PRECIPITATION(0) vs SNOW_ACCUMULATION(1) - PRECIPITATION이 우선
        String result = CommentProvider.provideComment(
                "1.2mm",       // PRECIPITATION LEVEL_2
                "0.7cm",       // SNOW_ACCUMULATION LEVEL_2
                0.5,           // WIND_SPEED LEVEL_1
                1.0            // TEMPERATURE LEVEL_1
        );
        assertEquals("바람막이를 챙겨가세요.", result); // PRECIPITATION의 LEVEL_2 메시지
    }

    @Test
    void testWindSpeedVsTemperatureSameLevel() {
        // WIND_SPEED(2) vs TEMPERATURE(3) - WIND_SPEED가 우선
        String result = CommentProvider.provideComment(
                "강수없음",      // PRECIPITATION LEVEL_0
                "적설없음",      // SNOW_ACCUMULATION LEVEL_0
                4.0,           // WIND_SPEED LEVEL_2
                5.0            // TEMPERATURE LEVEL_2
        );
        assertEquals("바람막이를 챙겨가세요.", result); // WIND_SPEED의 LEVEL_2 메시지
    }

    @Test
    void testBoundaryValues() {
        // 경계값 테스트
        String result = CommentProvider.provideComment(
                "1.0mm",       // PRECIPITATION LEVEL_2 (1.0-1.4 범위)
                "0.5cm",       // SNOW_ACCUMULATION LEVEL_2 (0.5-0.9 범위)
                3.0,           // WIND_SPEED LEVEL_2 (3-5.9 범위)
                4.0            // TEMPERATURE LEVEL_2 (4-6 범위)
        );
        assertEquals("바람막이를 챙겨가세요.", result);
    }

    @Test
    void testExtremeValues() {
        // 극한값 테스트
        String result = CommentProvider.provideComment(
                "10mm",        // PRECIPITATION LEVEL_4
                "5cm",         // SNOW_ACCUMULATION LEVEL_4
                20.0,          // WIND_SPEED LEVEL_4
                15.0           // TEMPERATURE LEVEL_4
        );
        assertEquals("오늘은 등산을 강력히 자제해 주세요.", result);
    }
}