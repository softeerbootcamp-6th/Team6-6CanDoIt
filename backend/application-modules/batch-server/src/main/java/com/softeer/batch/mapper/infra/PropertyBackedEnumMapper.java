package com.softeer.batch.mapper.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

import java.util.Map;

@RequiredArgsConstructor
public class PropertyBackedEnumMapper<E extends Enum<E>> implements CodeMapper<E> {
    private final Map<String, String> codeToEnumName;
    private final Class<E> enumType;

    @Override
    public E map(String raw) {
        if (raw == null) throw new IllegalArgumentException("raw code is null");
        String name = codeToEnumName.get(raw);
        if (name == null) {
            throw new ConversionFailedException(
                    TypeDescriptor.valueOf(String.class),
                    TypeDescriptor.valueOf(enumType),
                    raw,
                    new IllegalArgumentException("No mapping for code: " + raw)
            );
        }
        return Enum.valueOf(enumType, name);
    }
}
