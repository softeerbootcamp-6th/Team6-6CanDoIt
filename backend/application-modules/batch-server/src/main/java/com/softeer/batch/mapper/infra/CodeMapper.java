package com.softeer.batch.mapper.infra;

public interface CodeMapper<E extends Enum<E>> {

    E map(String raw);

    default E mapOrDefault(String raw, E fallback) {
        try { return map(raw); } catch (Exception e) { return fallback; }
    }
}
