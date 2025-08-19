package com.softeer.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReportWeatherKeywordId implements Serializable {
    @Column(name = "report_id")
    private long reportId;
    
    @Column(name = "weather_keyword_id")
    private int weatherKeywordId;
}