package com.softeer;

import com.softeer.scan.RedisKeyScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTestWithRedis
class RedisKeyScannerTest {

    @Autowired
    private RedisKeyScanner redisKeyScanner;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 삽입
        redisTemplate.opsForValue().set("forecast:123:SHORT:2025-08-14T10:00", "value1");
        redisTemplate.opsForValue().set("forecast:123:SHORT:2025-08-14T11:00", "value2");
        redisTemplate.opsForValue().set("forecast:124:MOUNTAIN:2025-08-14T11:00", "value3");
    }

    @Test
    @DisplayName("pattern에 맞는 key만 scanKeys로 가져온다")
    void testScanKeys() {
        String pattern = "forecast:123:SHORT:*";

        List<String> keys = redisKeyScanner.scanKeys(pattern);

        assertThat(keys).containsExactlyInAnyOrder(
                "forecast:123:SHORT:2025-08-14T10:00",
                "forecast:123:SHORT:2025-08-14T11:00"
        );
    }

    @Test
    @DisplayName("존재하지 않는 키 패턴을 scan할 경우 빈 리스트 반환")
    void testScanKeys_noMatch() {
        String pattern = "forecast:999:INVALID:*";

        List<String> keys = redisKeyScanner.scanKeys(pattern);

        assertThat(keys).isEmpty();
    }
}

