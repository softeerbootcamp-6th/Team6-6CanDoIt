package com.softeer.batch.mapper;

import com.softeer.batch.mapper.infra.CodeMapper;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ForecastMapper {

    private final Map<String, CodeMapper<?>> mapperRegistry;

    public Sky mapSky(String code) {
        return (Sky) mapperRegistry.get("SKY").map(code);
    }

    public WindDirection mapWindDirection(String code) {
        return (WindDirection) mapperRegistry.get("VEC").map(code);
    }

    public PrecipitationType mapPrecipitationType(String code) {
        return (PrecipitationType) mapperRegistry.get("PTY").map(code);
    }

    public PrecipitationType deriveMountainPrecipitationType(String pcp, String sno) {
        boolean hasRain = !Objects.equals(pcp, "강수없음");
        boolean hasSnow = !Objects.equals(sno, "적설없음");

        if (hasRain && hasSnow) return PrecipitationType.SLEET;
        else if (hasSnow)       return PrecipitationType.SNOW;
        else if (hasRain)       return PrecipitationType.RAIN;
        else                    return PrecipitationType.NONE;
    }
}
