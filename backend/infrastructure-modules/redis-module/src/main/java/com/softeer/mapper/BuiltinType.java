package com.softeer.mapper;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

public enum BuiltinType implements Type {

    STRING(String.class, TypeUtils::convertToString),
    INTEGER(Integer.class, int.class, TypeUtils::convertToInteger),
    LONG(Long.class, long.class, TypeUtils::convertToLong),
    DOUBLE(Double.class, double.class, TypeUtils::convertToDouble),
    SHORT(Short.class, short.class, TypeUtils::convertToShort),
    BOOLEAN(Boolean.class, boolean.class, TypeUtils::convertToBoolean),
    CHARACTER(Character.class, char.class, TypeUtils::convertToCharacter),
    ENUM(Enum.class, TypeUtils::convertToEnum) {
        @Override
        public boolean supports(Class<?> target) {
            return target.isEnum();
        }
    },
    LOCAL_DATE_TIME(LocalDateTime.class, TypeUtils::convertToLocalDateTime);

    private final Class<?>[] handledTypes;
    private final BiFunction<Object, Class<?>, Object> converter;

    BuiltinType(Class<?> type, BiFunction<Object, Class<?>, Object> converter) {
        this(converter, type);
    }

    BuiltinType(Class<?> type1, Class<?> type2, BiFunction<Object, Class<?>, Object> converter) {
        this(converter, type1, type2);
    }

    private BuiltinType(BiFunction<Object, Class<?>, Object> converter, Class<?>... handledTypes) {
        this.handledTypes = handledTypes;
        this.converter = converter;
    }

    @Override
    public Object convert(Object src, Class<?> targetType) {
        return converter.apply(src, targetType);
    }

    @Override
    public Class<?>[] handledTypes() {
        return handledTypes;
    }
}