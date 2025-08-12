package com.softeer.dto.response.card;

import com.softeer.domain.Forecast;
import com.softeer.service.ForecastUseCase;

public record ForecastCardResponse(ForecastCardDetail startCard, ForecastCardDetail arrivalCard,
                                   ForecastCardDetail adjustedArrivalCard, ForecastCardDetail descentCard,
                                   int courseAltitude
                                   ) {

    public static ForecastCardResponse from(ForecastUseCase.CourseForecast courseForecast, int courseAltitude, int mountainAltitude) {
        Forecast startForecast = courseForecast.startForecast();
        Forecast arrivalForecast = courseForecast.arrivalForecast();
        Forecast adjustedArrivalForecast = arrivalForecast.withAltitudeAdjustment(courseAltitude, mountainAltitude);
        Forecast descentForecast = courseForecast.descentForecast();

        return new ForecastCardResponse(
                ForecastCardDetail.from(startForecast), ForecastCardDetail.from(arrivalForecast),
                ForecastCardDetail.from(adjustedArrivalForecast), ForecastCardDetail.from(descentForecast),
                courseAltitude
        );
    }
}
