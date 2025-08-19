package com.softeer.batch.mapper;

import java.util.Map;

public final class MapperFixture {

    private MapperFixture() {}

    public static Map<String, String> createSKYMappings() {
        return Map.of(
                "1", "SUNNY",
                "3", "MOSTLY_CLOUDY",
                "4", "CLOUDY"
        );
    }

    public static Map<String, String> createPTYMappings() {
        return Map.of(
                "0", "NONE",
                "1", "RAIN",
                "2", "SLEET",
                "3", "SNOW"
        );
    }
}
