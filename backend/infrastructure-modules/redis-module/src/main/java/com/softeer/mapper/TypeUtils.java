package com.softeer.mapper;

public final class TypeUtils {
    private TypeUtils() {}

    public static Object convertToString(Object src, Class<?> targetType) {
        return String.valueOf(src);
    }

    public static Object convertToInteger(Object src, Class<?> targetType) {
        if (src instanceof Number number) {
            return number.intValue();
        }
        return Integer.parseInt(src.toString().trim());
    }

    public static Object convertToLong(Object src, Class<?> targetType) {
        if (src instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(src.toString().trim());
    }

    public static Object convertToDouble(Object src, Class<?> targetType) {
        if (src instanceof Number number) {
            return number.doubleValue();
        }
        return Double.parseDouble(src.toString().trim());
    }

    public static Object convertToShort(Object src, Class<?> targetType) {
        if (src instanceof Number number) {
            return number.shortValue();
        }
        return Short.parseShort(src.toString().trim());
    }

    public static Object convertToBoolean(Object src, Class<?> targetType) {
        if (src instanceof Boolean bool) {
            return bool;
        }
        if (src instanceof Number number) {
            return number.intValue() != 0;
        }
        return Boolean.parseBoolean(src.toString().trim());
    }

    public static Object convertToCharacter(Object src, Class<?> targetType) {
        if (src instanceof Character character) {
            return character;
        }

        String str = src.toString();
        if (str.length() == 1) {
            return str.charAt(0);
        }

        throw createTypeMismatchException(src, targetType);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object convertToEnum(Object src, Class<?> targetType) {
        if (!targetType.isEnum()) {
            throw createTypeMismatchException(src, targetType);
        }

        if (src instanceof Enum<?> enumValue) {
            return enumValue;
        }

        String enumName = src.toString().trim();
        return Enum.valueOf((Class<Enum>) targetType, enumName);
    }

    private static IllegalArgumentException createTypeMismatchException(Object value, Class<?> targetType) {
        String sourceType = value == null ? "null" : value.getClass().getSimpleName();
        String targetTypeName = targetType.getSimpleName();

        return new IllegalArgumentException(
                String.format("Cannot convert %s to %s", sourceType, targetTypeName)
        );
    }
}
