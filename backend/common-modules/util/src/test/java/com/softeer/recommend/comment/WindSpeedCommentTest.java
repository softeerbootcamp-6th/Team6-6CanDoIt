package com.softeer.recommend.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WindSpeedCommentTest {

    @Test
    @DisplayName("풍속 0~2.9 사이는 LEVEL_1을 반환해야 한다.")
    void testLevel1() {
        assertEquals(WindSpeedComment.LEVEL_1, WindSpeedComment.findWindSpeedComment(1.5));
        assertEquals(WindSpeedComment.LEVEL_1, WindSpeedComment.findWindSpeedComment(0.0));
        assertEquals(WindSpeedComment.LEVEL_1, WindSpeedComment.findWindSpeedComment(2.9));
    }

    @Test
    @DisplayName("풍속 3~5.9 사이는 LEVEL_2를 반환해야 한다.")
    void testLevel2() {
        assertEquals(WindSpeedComment.LEVEL_2, WindSpeedComment.findWindSpeedComment(4.0));
        assertEquals(WindSpeedComment.LEVEL_2, WindSpeedComment.findWindSpeedComment(3.0));
        assertEquals(WindSpeedComment.LEVEL_2, WindSpeedComment.findWindSpeedComment(5.9));
    }

    @Test
    @DisplayName("풍속 6~13.9 사이는 LEVEL_3을 반환해야 한다.")
    void testLevel3() {
        assertEquals(WindSpeedComment.LEVEL_3, WindSpeedComment.findWindSpeedComment(10.0));
        assertEquals(WindSpeedComment.LEVEL_3, WindSpeedComment.findWindSpeedComment(6.0));
        assertEquals(WindSpeedComment.LEVEL_3, WindSpeedComment.findWindSpeedComment(13.9));
    }

    @Test
    @DisplayName("풍속 14.0 이상은 LEVEL_4를 반환해야 한다.")
    void testLevel4() {
        // 참고: WindSpeedComment.LEVEL_4의 isInRange 로직이 windSpeed >= 14.0 이어야 올바르게 동작합니다.
        assertEquals(WindSpeedComment.LEVEL_4, WindSpeedComment.findWindSpeedComment(20.0));
        assertEquals(WindSpeedComment.LEVEL_4, WindSpeedComment.findWindSpeedComment(14.0));
    }

    @Test
    @DisplayName("정의되지 않은 풍속(음수)은 IllegalArgumentException을 발생시켜야 한다.")
    void testInvalidRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            WindSpeedComment.findWindSpeedComment(-1.0);
        });
    }
}