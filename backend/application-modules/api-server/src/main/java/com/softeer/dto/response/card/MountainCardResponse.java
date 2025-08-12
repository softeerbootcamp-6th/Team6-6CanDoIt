package com.softeer.dto.response.card;

import com.softeer.domain.Mountain;
import com.softeer.service.ForecastUseCase;

public record MountainCardResponse(String mountainName, String mountainImageUrl, String mountainDescription, WeatherMetric weatherMetric) {
    public MountainCardResponse(Mountain mountain, ForecastUseCase.WeatherCondition weatherCondition) {
        this(mountain.name(), mountain.imageUrl(), mountain.description(), new WeatherMetric(weatherCondition));
    }
}
