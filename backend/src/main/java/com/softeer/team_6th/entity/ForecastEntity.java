package com.softeer.team_6th.entity;

import com.softeer.team_6th.domain.Forecast;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 예보 JPA 엔티티
 * 기상 예보 정보를 저장하는 엔티티
 */
@Entity
@Table(name = "forecasts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForecastEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "grid_id", nullable = false)
    private Long gridId;  // 그리드 ID (참조)
    
    @Column(name = "base_date_time", nullable = false)
    private LocalDateTime baseDateTime;  // 예보 기준 시간
    
    @Column(name = "forecast_date_time", nullable = false)
    private LocalDateTime forecastDateTime;  // 예보 시간
    
    @Column(nullable = false)
    private String category;  // 예보 항목
    
    @Column(name = "forecast_value", nullable = false)
    private String forecastValue;  // 예보값
    
    @Builder
    public ForecastEntity(Long gridId, LocalDateTime baseDateTime, LocalDateTime forecastDateTime,
                         String category, String forecastValue) {
        this.gridId = gridId;
        this.baseDateTime = baseDateTime;
        this.forecastDateTime = forecastDateTime;
        this.category = category;
        this.forecastValue = forecastValue;
    }
    
    /**
     * JPA 엔티티를 도메인 객체로 변환
     */
    public Forecast toDomain() {
        return Forecast.builder()
                .id(this.id)
                .gridId(this.gridId)
                .baseDateTime(this.baseDateTime)
                .forecastDateTime(this.forecastDateTime)
                .category(this.category)
                .forecastValue(this.forecastValue)
                .build();
    }
    
    /**
     * 도메인 객체로부터 JPA 엔티티 생성
     */
    public static ForecastEntity fromDomain(Forecast forecast) {
        return ForecastEntity.builder()
                .gridId(forecast.getGridId())
                .baseDateTime(forecast.getBaseDateTime())
                .forecastDateTime(forecast.getForecastDateTime())
                .category(forecast.getCategory())
                .forecastValue(forecast.getForecastValue())
                .build();
    }
}