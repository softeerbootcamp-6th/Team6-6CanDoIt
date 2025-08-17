package com.softeer.scan;

import com.softeer.mapper.RecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RedisDataScanner {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RecordMapper recordMapper;

    @SuppressWarnings("unchecked")
    public <T extends Record> List<T> scanHashData(List<String> keys, Class<T> clazz) {
        if (keys == null || keys.isEmpty()) return Collections.emptyList();
        if (!clazz.isRecord()) {
            throw new IllegalArgumentException("Only record classes are supported");
        }

        RedisSerializer<String> stringSer = redisTemplate.getStringSerializer();

        List<Object> rawResults = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (String key : keys) {
                byte[] rawKey = stringSer.serialize(key);
                connection.hGetAll(rawKey);
            }
            return null;
        });

        // 2) 결과: Map<String, Object>로 받아서 record로 매핑
        List<T> resultList = new ArrayList<>(rawResults.size());

        for (Object raw : rawResults) {
            if (raw == null) {
                resultList.add(null);
                continue;
            }

            Map<String, Object> hash = (Map<String, Object>) raw;

            if (hash.isEmpty()) {
                resultList.add(null);
                continue;
            }

            T record = recordMapper.mapToRecord(hash, clazz);
            resultList.add(record);
        }

        return resultList;
    }
}
