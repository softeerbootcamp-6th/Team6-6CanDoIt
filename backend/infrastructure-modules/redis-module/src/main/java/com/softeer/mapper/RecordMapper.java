package com.softeer.mapper;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RecordMapper {

    private final TypeConverterRegistry registry;
    private final StringRedisSerializer keySerializer;
    private final GenericJackson2JsonRedisSerializer valueSerializer;

    public <T extends  Record> T mapToRecord(Map<String, Object> map, Class<T> recordClass) {
        if (!recordClass.isRecord()) {
            throw new IllegalArgumentException("Provided class is not a record: " + recordClass);
        }

        try {
            RecordComponent[] recordComponents = recordClass.getRecordComponents();
            Object[] args = new Object[recordComponents.length];
            Class<?>[] parameterTypes = new Class<?>[recordComponents.length];

            for (int i = 0; i < recordComponents.length; i++) {
                RecordComponent recordComponent = recordComponents[i];
                String name = recordComponent.getName();
                Class<?> targetType = recordComponent.getType();
                Object value = map.get(name);

                if (value == null || StringUtils.isEmpty(value.toString())) {
                    if (targetType.isPrimitive()) throw new IllegalArgumentException("Null for primitive field: " + name);
                    args[i] = null;
                } else {
                    Type converter = registry.getConverter(targetType).orElseThrow(() -> new IllegalArgumentException("No converter for " + targetType));
                    args[i] = converter.convert(value, targetType);
                }
                parameterTypes[i] = targetType;
            }

            Constructor<T> constructor = recordClass.getDeclaredConstructor(parameterTypes);
            return constructor.newInstance(args);

        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create record instance", e);
        }
    }

    public <T extends Record> Map<String, Object> toMap(T record) {
        if (!record.getClass().isRecord()) {
            throw new IllegalArgumentException("Provided object is not a record: " + record.getClass());
        }

        Map<String, Object> map = new HashMap<>();

        for (RecordComponent component : record.getClass().getRecordComponents()) {
            try {
                Method accessor = component.getAccessor();
                Object value = accessor.invoke(record);
                map.put(component.getName(), value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to access record component: " + component.getName(), e);
            }
        }

        return map;
    }

    public <T extends Record> Map<byte[], byte[]>  toByteMap(T record) {
        if (!record.getClass().isRecord()) {
            throw new IllegalArgumentException("Provided object is not a record: " + record.getClass());
        }

        Map<byte[], byte[]> map = new HashMap<>();

        for (RecordComponent component : record.getClass().getRecordComponents()) {
            try {
                Method accessor = component.getAccessor();
                Object value = accessor.invoke(record);

                byte[] hashKey = keySerializer.serialize(component.getName());
                byte[] hashValue = valueSerializer.serialize(value);
                map.put(hashKey, hashValue);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to access record component: " + component.getName(), e);
            }
        }

        return map;
    }

    public byte[] serializeValue(Object object) {
        if (object == null) {
            return new byte[0];
        }
        return valueSerializer.serialize(object);
    }
}
