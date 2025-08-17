package com.softeer.scan;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisKeyScanner {

    private final RedisConnectionFactory connectionFactory;

    public List<String> scanKeys(String pattern, int count) {

        List<String> result = new ArrayList<>();

        try (RedisConnection connection = connectionFactory.getConnection()) {
            ScanOptions options = ScanOptions.scanOptions()
                    .match(pattern)
                    .count(count)
                    .build();

            try (Cursor<byte[]> cursor = connection.scan(options)) {
                cursor.forEachRemaining(key -> result.add(new String(key, StandardCharsets.UTF_8)));
            }
        }

        return result;
    }

    public List<String> scanKeys(String pattern) {

        List<String> result = new ArrayList<>();

        try (RedisConnection connection = connectionFactory.getConnection()) {
            ScanOptions options = ScanOptions.scanOptions()
                    .match(pattern)
                    .count(1000)
                    .build();

            try (Cursor<byte[]> cursor = connection.scan(options)) {
                cursor.forEachRemaining(key -> result.add(new String(key, StandardCharsets.UTF_8)));
            }
        }

        return result;
    }
}
