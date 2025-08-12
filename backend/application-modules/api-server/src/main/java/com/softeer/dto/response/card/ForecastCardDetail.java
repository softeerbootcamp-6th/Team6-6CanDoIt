package com.softeer.dto.response.card;

import com.softeer.domain.DailyTemperature;
import com.softeer.domain.Forecast;
import com.softeer.domain.condition.*;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;

import java.time.LocalDateTime;

public record ForecastCardDetail(LocalDateTime dateTime, String hikingActivity,
                                 double temperature, double apparentTemperature,String temperatureDescription,
                                 String precipitation, String probabilityDescription,
                                 PrecipitationType precipitationType, Sky sky, String skyDescription,
                                 double windSpeed, String windSpeedDescription,
                                 double humidity, String humidityDescription,
                                 double highestTemperature, double lowestTemperature
                                 ) {

    public static ForecastCardDetail from(Forecast forecast) {
        TemperatureCondition temperatureCondition = forecast.temperatureCondition();
        PrecipitationCondition precipitationCondition = forecast.precipitationCondition();
        SkyCondition skyCondition = forecast.skyCondition();
        WindCondition windCondition = forecast.windCondition();
        HumidityCondition humidityCondition = forecast.humidityCondition();
        DailyTemperature dailyTemperature = forecast.dailyTemperature();

        return new ForecastCardDetail(forecast.dateTime(), forecast.getHikingActivityStatus(),
                temperatureCondition.temperature(), temperatureCondition.apparentTemperature(), temperatureCondition.displayDescription(),
                precipitationCondition.precipitation(), precipitationCondition.displayDescription(),
                precipitationCondition.precipitationType(), skyCondition.sky(), skyCondition.displayDescription(),
                windCondition.windSpeed(), windCondition.displayDescription(),
                humidityCondition.humidity(), humidityCondition.displayDescription(),
                dailyTemperature.highestTemperature(), dailyTemperature.lowestTemperature()
        );
    }
}
