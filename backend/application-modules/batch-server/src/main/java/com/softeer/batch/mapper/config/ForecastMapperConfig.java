package com.softeer.batch.mapper.config;

import com.softeer.batch.mapper.infra.CodeMapper;
import com.softeer.batch.mapper.impl.DegreeWindDirectionMapper;
import com.softeer.batch.mapper.infra.PropertyBackedEnumMapper;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(ForecastMappingProperties.class)
public class ForecastMapperConfig {

    @Bean
    public CodeMapper<Sky> skyMapper(ForecastMappingProperties props) {
        return new PropertyBackedEnumMapper<>(props.getSky(), Sky.class);
    }

    @Bean
    public CodeMapper<WindDirection> windDirectionMapper() {
        return new DegreeWindDirectionMapper();
    }

    @Bean
    public CodeMapper<PrecipitationType> precipitationTypeMapper(ForecastMappingProperties props) {
        return new PropertyBackedEnumMapper<>(props.getPrecipitationType(), PrecipitationType.class);
    }

    @Bean
    public Map<String, CodeMapper<?>> mapperRegistry(
            CodeMapper<Sky> sky,
            CodeMapper<WindDirection> wd,
            CodeMapper<PrecipitationType> pty
    ) {
        return Map.of(
                "SKY", sky,
                "VEC", wd,
                "PTY", pty
        );
    }
}
