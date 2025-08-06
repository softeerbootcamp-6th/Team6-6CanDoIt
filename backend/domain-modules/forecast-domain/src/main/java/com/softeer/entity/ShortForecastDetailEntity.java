package com.softeer.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "short_forecast_detail")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ShortForecastDetailEntity {

    @Id
    @Column(name = "forecast_id")
    private long forecastId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_id")
    private ForecastEntity forecast;

    @Column(name = "precipitation_probability")
    private double precipitationProbability;

    @Column(name = "snow_accumulation")
    private double snowAccumulation;

    @Column(name = "highest_temperature")
    private double highestTemperature;

    @Column(name = "lowest_temperature")
    private double lowestTemperature;

    public ShortForecastDetailEntity(ForecastEntity forecast, double precipitationProbability,  double snowAccumulation,
                                     double highestTemperature, double lowestTemperature
                                     ) {
        this.forecast = forecast;
        this.precipitationProbability = precipitationProbability;
        this.snowAccumulation = snowAccumulation;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
    }
}
