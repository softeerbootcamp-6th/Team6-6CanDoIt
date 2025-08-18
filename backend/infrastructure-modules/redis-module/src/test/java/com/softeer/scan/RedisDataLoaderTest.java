package com.softeer.scan;

import com.softeer.SpringBootTestWithRedis;
import com.softeer.mapper.RecordMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;

import static com.softeer.scan.RedisDataLoaderTest.Role.ADMIN;
import static org.assertj.core.api.Assertions.*;

@SpringBootTestWithRedis
public class RedisDataLoaderTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    RedisDataLoader scanner;
    @Autowired
    RecordMapper recordMapper;

    @Test
    @DisplayName("두 해시 키를 User record로 매핑")
    void loadEntities_happyPath() {
        User alice = new User("Alice", 30, true, ADMIN);
        redisTemplate.opsForHash().putAll("user:1", recordMapper.toMap(alice));
        User bob = new User("Bob", 40, true, ADMIN);
        redisTemplate.opsForHash().putAll("user:2", recordMapper.toMap(bob));

        List<User> out = scanner.loadEntities(List.of("user:1", "user:2"), User.class);

        assertThat(out).hasSize(2);
        assertThat(out.get(0)).isEqualTo(alice);
        assertThat(out.get(1)).isEqualTo(bob);
    }

    @Test
    @DisplayName("없는 키(빈 해시)는 null로 들어간다")
    void loadEntities_missingKeyBecomesNull() {
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


        assertThatThrownBy(() -> scanner.loadEntities(List.of("hash:user:1", "hash:user:404", "hash:user:2")
                , User.class))
                .isInstanceOf(RuntimeException.class);
    }

    public record User(String name, int age, boolean active, Role role) implements Comparable<User> {
        @Override
        public int compareTo(User o) {
            return age - o.age;
        }
    }
    enum Role { USER, ADMIN }
}
