package com.softeer.batch.mapper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

@Getter @Setter
@ConfigurationProperties(prefix = "forecast.mapping")
public class ForecastMappingProperties {
    private Map<String, String> sky;
    private Map<String, String> windDirection;
    private Map<String, String> precipitationType;
}
