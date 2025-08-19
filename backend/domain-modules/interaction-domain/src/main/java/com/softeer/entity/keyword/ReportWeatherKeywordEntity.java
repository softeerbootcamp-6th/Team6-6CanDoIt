package com.softeer.entity.keyword;

import com.softeer.entity.id.ReportWeatherKeywordId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_weather_keyword")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportWeatherKeywordEntity {
    @EmbeddedId
    private ReportWeatherKeywordId id;
}