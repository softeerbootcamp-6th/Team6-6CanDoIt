package com.softeer.batch.forecast.shortterm.dto;

import com.softeer.domain.Forecast;

import java.util.List;

public record ShortForecastList(
        int gridId,
        List<Forecast> forecasts
) {
}
