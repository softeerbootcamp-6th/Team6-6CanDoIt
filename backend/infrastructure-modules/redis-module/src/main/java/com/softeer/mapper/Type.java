package com.softeer.mapper;

public interface Type {

    Class<?>[] handledTypes();

    Object convert(Object src, Class<?> targetType);

    default boolean supports(Class<?> target) {
        Class<?> boxed = boxIfPrimitive(target);
        for (Class<?> c : handledTypes()) {
            if (c == boxed) return true;
        }
        return false;
    }

    static Class<?> boxIfPrimitive(Class<?> type) {
        if (!type.isPrimitive()) return type;
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == double.class) return Double.class;
        if (type == float.class) return Float.class;
        if (type == boolean.class) return Boolean.class;
        if (type == char.class) return Character.class;
        if (type == byte.class) return Byte.class;
        if (type == short.class) return Short.class;
        return type;
    }
}
