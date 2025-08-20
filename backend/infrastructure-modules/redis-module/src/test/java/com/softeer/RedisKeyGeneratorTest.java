package com.softeer;

import com.softeer.scan.RedisKeyGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RedisKeyGeneratorTest {

    private final RedisKeyGenerator redisKeyGenerator = new RedisKeyGenerator(mock(StringRedisSerializer.class));

    @Test
    @DisplayName("generateKey: forecast:123:SHORT:2025-08-14T10:00 생성")
    void generateKeyTest() {
        int gridId = 123;
        String type = "SHORT";
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 14, 10, 0);

        String key = redisKeyGenerator.generateKey("forecast", gridId, type, dateTime);

        assertThat(key).isEqualTo("forecast:123:SHORT:2025-08-14T10:00");
    }

    @Test
    @DisplayName("generateScanPattern: forecast:123:SHORT:* 생성")
    void generateScanPatternTest() {
        int gridId = 123;
        String type = "SHORT";

        String pattern = redisKeyGenerator.generateScanPattern("forecast", gridId, type);

        assertThat(pattern).isEqualTo("forecast:123:SHORT:*");
    }
}
