package com.softeer.batch.mapper;

import com.softeer.batch.mapper.impl.DegreeWindDirectionMapper;
import com.softeer.batch.mapper.infra.CodeMapper;
import com.softeer.batch.mapper.infra.PropertyBackedEnumMapper;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
class ForecastMapperTest {

    private ForecastMapper target;

    @BeforeEach
    void setUp() {
        CodeMapper<Sky> skyMapper = new PropertyBackedEnumMapper<>(MapperFixture.createSKYMappings(), Sky.class);
        CodeMapper<WindDirection> windDirectionMapper = new DegreeWindDirectionMapper();
        CodeMapper<PrecipitationType> precipitationTypeMapper = new PropertyBackedEnumMapper<>(MapperFixture.createPTYMappings(), PrecipitationType.class);

        Map<String, CodeMapper<?>> mapperRegistry = Map.of(
                "SKY", skyMapper,
                "VEC", windDirectionMapper,
                "PTY", precipitationTypeMapper
        );

        this.target = new ForecastMapper(mapperRegistry);
    }

    @Nested
    @DisplayName("코드 매핑 기능 테스트")
    class Describe_mapping {

        @DisplayName("하늘 상태 코드(SKY)를 Enum으로 정확히 변환해야 한다")
        @Test
        void mapSky_ShouldConvertCodeToSkyEnum() {
            assertEquals(Sky.SUNNY, target.mapSky("1"));
            assertEquals(Sky.CLOUDY, target.mapSky("4"));
        }

        @DisplayName("정의되지 않은 하늘 상태 코드가 들어오면 예외가 발생해야 한다")
        @Test
        void mapSky_ShouldThrowExceptionForUnknownCode() {
            assertThrows(Exception.class, () -> target.mapSky("99"));
        }

        @DisplayName("풍향 각도(VEC)를 16방위 Enum으로 정확히 변환해야 한다")
        @ParameterizedTest(name = "{0}도는 {1}로 변환되어야 함")
        @CsvSource({
                "0,    N",
                "11.24, N",
                "11.25, NNE",
                "45,   NE",
                "90,   E",
                "180,  S",
                "270,  W",
                "348.74, NNW",
                "348.75, N"
        })
        void mapWindDirection_ShouldConvertDegreeToWindDirectionEnum(String degree, WindDirection expected) {
            WindDirection result = target.mapWindDirection(degree);
            assertEquals(expected, result);
        }

        @DisplayName("강수 형태 코드(PTY)를 Enum으로 정확히 변환해야 한다")
        @Test
        void mapPrecipitationType_ShouldConvertCodeToPtyEnum() {
            assertEquals(PrecipitationType.NONE, target.mapPrecipitationType("0"));
            assertEquals(PrecipitationType.RAIN, target.mapPrecipitationType("1"));
            assertEquals(PrecipitationType.SNOW, target.mapPrecipitationType("3"));
        }
    }


}
