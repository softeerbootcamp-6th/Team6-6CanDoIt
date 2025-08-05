package com.softeer.team_6th.service;

import com.softeer.team_6th.domain.Forecast;
import com.softeer.team_6th.entity.ForecastEntity;
import com.softeer.team_6th.repository.ForecastRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * ForecastService 테스트
 */
@ExtendWith(MockitoExtension.class)
class ForecastServiceTest {

    @Mock
    private ForecastRepository forecastRepository;
    
    @InjectMocks
    private ForecastService forecastService;

    @Test
    void 그리드_ID로_예보_조회_테스트() {
        // given
        Long gridId = 1L;
        LocalDateTime baseDateTime = LocalDateTime.of(2024, 1, 1, 5, 0);
        LocalDateTime forecastDateTime = LocalDateTime.of(2024, 1, 1, 6, 0);
        
        ForecastEntity entity1 = ForecastEntity.builder()
                .gridId(gridId)
                .baseDateTime(baseDateTime)
                .forecastDateTime(forecastDateTime)
                .category("TMP")
                .forecastValue("15")
                .build();
        
        ForecastEntity entity2 = ForecastEntity.builder()
                .gridId(gridId)
                .baseDateTime(baseDateTime)
                .forecastDateTime(forecastDateTime)
                .category("SKY")
                .forecastValue("1")
                .build();
        
        when(forecastRepository.findByGridId(gridId)).thenReturn(Arrays.asList(entity1, entity2));
        
        // when
        List<Forecast> forecasts = forecastService.findForecastsByGridId(gridId);
        
        // then
        assertThat(forecasts).hasSize(2);
        assertThat(forecasts.get(0).getCategory()).isEqualTo("TMP");
        assertThat(forecasts.get(0).getForecastValue()).isEqualTo("15");
        assertThat(forecasts.get(1).getCategory()).isEqualTo("SKY");
        assertThat(forecasts.get(1).getForecastValue()).isEqualTo("1");
    }

    @Test
    void 카테고리별_예보_조회_테스트() {
        // given
        Long gridId = 1L;
        String category = "TMP";
        LocalDateTime baseDateTime = LocalDateTime.of(2024, 1, 1, 5, 0);
        LocalDateTime forecastDateTime = LocalDateTime.of(2024, 1, 1, 6, 0);
        
        ForecastEntity entity = ForecastEntity.builder()
                .gridId(gridId)
                .baseDateTime(baseDateTime)
                .forecastDateTime(forecastDateTime)
                .category(category)
                .forecastValue("15")
                .build();
        
        when(forecastRepository.findByGridIdAndCategory(gridId, category))
                .thenReturn(Arrays.asList(entity));
        
        // when
        List<Forecast> forecasts = forecastService.findForecastsByGridIdAndCategory(gridId, category);
        
        // then
        assertThat(forecasts).hasSize(1);
        assertThat(forecasts.get(0).getCategory()).isEqualTo("TMP");
        assertThat(forecasts.get(0).getForecastValue()).isEqualTo("15");
    }

    @Test
    void 예보_저장_테스트() {
        // given
        Forecast forecast = Forecast.builder()
                .gridId(1L)
                .baseDateTime(LocalDateTime.of(2024, 1, 1, 5, 0))
                .forecastDateTime(LocalDateTime.of(2024, 1, 1, 6, 0))
                .category("TMP")
                .forecastValue("15")
                .build();
        
        ForecastEntity savedEntity = ForecastEntity.builder()
                .gridId(1L)
                .baseDateTime(LocalDateTime.of(2024, 1, 1, 5, 0))
                .forecastDateTime(LocalDateTime.of(2024, 1, 1, 6, 0))
                .category("TMP")
                .forecastValue("15")
                .build();
        
        when(forecastRepository.save(any(ForecastEntity.class))).thenReturn(savedEntity);
        
        // when
        Forecast result = forecastService.saveForecast(forecast);
        
        // then
        assertThat(result.getGridId()).isEqualTo(1L);
        assertThat(result.getCategory()).isEqualTo("TMP");
        assertThat(result.getForecastValue()).isEqualTo("15");
    }
}