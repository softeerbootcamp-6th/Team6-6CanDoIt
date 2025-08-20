package com.softeer.scan;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class RedisKeyGenerator {

    private static final String DELIMITER = ":";
    private static final String ALL = "*";
    private final StringRedisSerializer keySerializer;

    public byte[] serializeKey(String prefix, Object... args) {
        return keySerializer.serialize(generateKey(prefix, args));
    }

    public String generateKey(String prefix, Object... args) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(prefix);
        for (Object arg : args) {
            joiner.add(arg.toString());
        }
        return joiner.toString();
    }

    public String generateScanPattern(String prefix, Object... args) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(prefix);
        for (Object arg : args) {
            joiner.add(arg.toString());
        }
        joiner.add(ALL);
        return joiner.toString();
    }
}
