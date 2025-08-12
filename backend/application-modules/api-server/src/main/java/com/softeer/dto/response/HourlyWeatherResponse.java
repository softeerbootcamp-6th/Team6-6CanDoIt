package com.softeer.dto.response;

import com.softeer.domain.Forecast;
import com.softeer.domain.condition.PrecipitationCondition;
import com.softeer.domain.condition.SkyCondition;
import com.softeer.domain.condition.TemperatureCondition;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;

import java.time.LocalDateTime;

public record HourlyWeatherResponse(LocalDateTime dateTime, double temperature, Sky sky, PrecipitationType  precipitationType) {

    public HourlyWeatherResponse(LocalDateTime dateTime, TemperatureCondition temperatureCondition, SkyCondition skyCondition, PrecipitationCondition precipitationCondition) {
        this(dateTime, temperatureCondition.temperature(), skyCondition.sky(), precipitationCondition.precipitationType());
    }

    public HourlyWeatherResponse(Forecast forecast) {
        this(forecast.dateTime(), forecast.temperatureCondition(), forecast.skyCondition(), forecast.precipitationCondition());
    }
}
