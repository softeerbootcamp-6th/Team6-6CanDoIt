package com.softeer.team_6th.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Forecast 도메인 테스트
 */
class ForecastTest {

    @Test
    void 예보_도메인_생성_테스트() {
        // given
        Long id = 1L;
        Long gridId = 1L;
        LocalDateTime baseDateTime = LocalDateTime.of(2024, 1, 1, 5, 0);
        LocalDateTime forecastDateTime = LocalDateTime.of(2024, 1, 1, 6, 0);
        String category = "TMP";
        String forecastValue = "15";
        
        // when
        Forecast forecast = Forecast.builder()
                .id(id)
                .gridId(gridId)
                .baseDateTime(baseDateTime)
                .forecastDateTime(forecastDateTime)
                .category(category)
                .forecastValue(forecastValue)
                .build();
        
        // then
        assertThat(forecast.getId()).isEqualTo(id);
        assertThat(forecast.getGridId()).isEqualTo(gridId);
        assertThat(forecast.getBaseDateTime()).isEqualTo(baseDateTime);
        assertThat(forecast.getForecastDateTime()).isEqualTo(forecastDateTime);
        assertThat(forecast.getCategory()).isEqualTo(category);
        assertThat(forecast.getForecastValue()).isEqualTo(forecastValue);
    }
}