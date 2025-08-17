package com.softeer.scan;

import com.softeer.SpringBootTestWithRedis;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;

import static com.softeer.scan.RedisDataScannerTest.Role.ADMIN;
import static org.assertj.core.api.Assertions.*;

@SpringBootTestWithRedis
public class RedisDataScannerTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    RedisDataScanner scanner;

    @Test
    @DisplayName("두 해시 키를 User record로 매핑")
    void scanHashData_happyPath() {
        HashOperations<String, String, Object> h = redisTemplate.opsForHash();

        h.put("hash:user:1", "name", "Alice");
        h.put("hash:user:1", "age", 30);
        h.put("hash:user:1", "active", "true");
        h.put("hash:user:1", "role", ADMIN);

        h.put("hash:user:2", "name", "Bob");
        h.put("hash:user:2", "age", "25");
        h.put("hash:user:2", "active", "false");
        h.put("hash:user:2", "role", "USER");

        List<User> out = scanner.scanHashData(List.of("hash:user:1", "hash:user:2"), User.class);

        assertThat(out).hasSize(2);
        assertThat(out.get(0)).isEqualTo(new User("Alice", 30, true, ADMIN));
        assertThat(out.get(1)).isEqualTo(new User("Bob", 25, false, Role.USER));
    }

    @Test
    @DisplayName("없는 키(빈 해시)는 null로 들어간다")
    void scanHashData_missingKeyBecomesNull() {
        HashOperations<String, String, Object> h = redisTemplate.opsForHash();

        h.put("hash:user:1", "name", "Alice");
        h.put("hash:user:1", "age", "30");
        h.put("hash:user:1", "active", "true");
        h.put("hash:user:1", "role", "ADMIN");

        // hash:user:404는 생성하지 않음 (빈 해시 응답)
        h.put("hash:user:2", "name", "Bob");
        h.put("hash:user:2", "age", 25);
        h.put("hash:user:2", "active", false);
        h.put("hash:user:2", "role", "USER");

        List<User> out = scanner.scanHashData(
                List.of("hash:user:1", "hash:user:404", "hash:user:2"),
                User.class
        );

        out.forEach(System.out::println);
        assertThat(out).hasSize(3);
        assertThat(out.get(0)).isEqualTo(new User("Alice", 30, true, ADMIN));
        assertThat(out.get(1)).isNull();
        assertThat(out.get(2)).isEqualTo(new User("Bob", 25, false, Role.USER));
    }

    @Test
    @DisplayName("primitive 필드에 빈 문자열이 오면 예외")
    void scanHashData_blankPrimitiveThrows() {
        HashOperations<String, String, String> h = redisTemplate.opsForHash();

        h.put("hash:user:1", "name", "Alice");
        h.put("hash:user:1", "age", "");          // <- 빈 문자열
        h.put("hash:user:1", "active", "true");
        h.put("hash:user:1", "role", "ADMIN");

        assertThatThrownBy(() -> scanner.scanHashData(List.of("hash:user:1"), User.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Null for primitive field: ");
    }

    public record User(String name, int age, boolean active, Role role) {}
    enum Role { USER, ADMIN }
}
