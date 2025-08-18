package com.softeer.scan;

import com.softeer.mapper.RecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisDataLoader {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RecordMapper recordMapper;

    /**
     * 여러 Redis 키로부터 엔티티들을 배치로 로드합니다.
     *
     * @param keys Redis 키 목록
     * @param clazz Record 클래스 타입
     * @return 변환된 엔티티 목록 (존재하지 않는 키는 null로 표시)
     */
    public <T extends Record & Comparable<T>> List<T> loadEntities(List<String> keys, Class<T> clazz) {
        if (isEmptyKeys(keys)) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> rawHashMaps = fetchHashMapsInBatch(keys);

        return convertHashMapsToRecords(rawHashMaps, clazz);
    }

    /**
     * 단일 Redis 키로부터 엔티티를 로드합니다.
     *
     * @param key Redis 키
     * @param clazz Record 클래스 타입
     * @return 변환된 엔티티 (존재하지 않으면 null)
     */
    public <T extends Record & Comparable<T>> T loadEntity(String key, Class<T> clazz) {
        if (key == null || key.trim().isEmpty()) {
            return null;
        }

        Map<String, Object> hashMap = fetchSingleHashMap(key);

        return convertHashMapToRecord(hashMap, clazz);
    }

    private boolean isEmptyKeys(List<String> keys) {
        return keys == null || keys.isEmpty();
    }

    private List<Map<String, Object>> fetchHashMapsInBatch(List<String> keys) {
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();

        List<Object> pipelineResults = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (String key : keys) {
                byte[] serializedKey = stringSerializer.serialize(key);
                connection.hGetAll(serializedKey);
            }
            return null;
        });

        return convertPipelineResultsToHashMaps(pipelineResults);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> convertPipelineResultsToHashMaps(List<Object> pipelineResults) {
        List<Map<String, Object>> hashMaps = new ArrayList<>(pipelineResults.size());

        for (Object result : pipelineResults) {
            if (result == null || !(result instanceof Map)) {
                hashMaps.add(null);
                continue;
            }

            Map<String, Object> hashMap = (Map<String, Object>) result;
            hashMaps.add(hashMap.isEmpty() ? null : hashMap);
        }

        return hashMaps;
    }

    private <T extends Record & Comparable<T>> List<T> convertHashMapsToRecords(List<Map<String, Object>> hashMaps, Class<T> clazz) {
        List<T> records = new ArrayList<>(hashMaps.size());

        for (Map<String, Object> hashMap : hashMaps) {
            T record = convertHashMapToRecord(hashMap, clazz);
            records.add(record);
        }

        records.sort(Comparator.naturalOrder());
        return records;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchSingleHashMap(String key) {
        try {
            Object rawData = redisTemplate.opsForHash().entries(key);

            if (!(rawData instanceof Map)) {
                log.warn("Redis에서 가져온 데이터가 Map 타입이 아닙니다. key: {}, type: {}", key, rawData != null ? rawData.getClass().getSimpleName() : "null");
                return null;
            }

            Map<String, Object> hashMap = (Map<String, Object>) rawData;
            return hashMap.isEmpty() ? null : hashMap;

        } catch (Exception e) {
            log.error("Redis에서 데이터를 가져오는 중 오류가 발생했습니다. key: {}", key, e);
            throw new RuntimeException("Redis 데이터 로드 실패: " + key, e);
        }
    }

    private <T extends Record> T convertHashMapToRecord(Map<String, Object> hashMap, Class<T> clazz) {
        if (hashMap == null) {
            return null;
        }

        try {
            return recordMapper.mapToRecord(hashMap, clazz);
        } catch (Exception e) {
            log.error("HashMap을 Record로 변환하는 중 오류가 발생했습니다. class: {}, data: {}", clazz.getSimpleName(), hashMap, e);
            throw new RuntimeException("Record 변환 실패: " + clazz.getSimpleName(), e);
        }
    }
}