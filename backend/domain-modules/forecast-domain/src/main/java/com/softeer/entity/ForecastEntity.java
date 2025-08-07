package com.softeer.entity;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "forecast")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ForecastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double temperature;

    private String precipitation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sky sky;

    private double humidity;

    @Enumerated(EnumType.STRING)
    @Column(name = "precipitation_type", nullable = false)
    private PrecipitationType precipitationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "wind_dir", nullable = false)
    private WindDirection windDir;

    private double windSpeed;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ForecastType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grid_id")
    private GridEntity gridEntity;

    public static ForecastEntity from(Forecast forecast, Grid grid) {
        return new ForecastEntity(
                forecast.id(),
                forecast.temperatureCondition().temperature(),
                forecast.precipitationCondition().precipitation(),
                forecast.skyCondition().sky(),
                forecast.humidityCondition().humidity(),
                forecast.precipitationCondition().precipitationType(),
                forecast.windCondition().direction(),
                forecast.windCondition().windSpeed(),
                forecast.dateTime(),
                forecast.forecastType(),
                GridEntity.from(grid)
        );
    }
}
