package com.softeer.mapper;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TypeConverterRegistry {

    private final Map<Class<?>, Type> byType = new HashMap<>();

    @PostConstruct
    public void init() {
        for (Type type : BuiltinType.values()) {
            for (Class<?> c : type.handledTypes()) {
                byType.put(boxIfPrimitive(c), type);
            }
        }
    }

    public Optional<Type> getConverter(Class<?> targetType) {
        if (targetType.isEnum()) return Optional.of(BuiltinType.ENUM);
        return Optional.ofNullable(byType.get(boxIfPrimitive(targetType)));
    }

    private static Class<?> boxIfPrimitive(Class<?> type) {
        return Type.boxIfPrimitive(type);
    }
}
