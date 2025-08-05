package com.softeer.team_6th.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * 예보 도메인 객체
 * 기상 예보 정보를 나타내는 도메인
 */
@Getter
@Builder
public class Forecast {
    private final Long id;
    private final Long gridId;  // 그리드 ID (참조)
    private final LocalDateTime baseDateTime;  // 예보 기준 시간
    private final LocalDateTime forecastDateTime;  // 예보 시간
    private final String category;  // 예보 항목 (POP, PTY, PCP, REH, SNO, SKY, TMP, TMN, TMX, UUU, VVV, WAV, VEC, WSD)
    private final String forecastValue;  // 예보값
    
    public Forecast(Long id, Long gridId, LocalDateTime baseDateTime, LocalDateTime forecastDateTime, 
                   String category, String forecastValue) {
        this.id = id;
        this.gridId = gridId;
        this.baseDateTime = baseDateTime;
        this.forecastDateTime = forecastDateTime;
        this.category = category;
        this.forecastValue = forecastValue;
    }
}