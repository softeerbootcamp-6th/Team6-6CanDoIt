package com.softeer.domain;

import com.softeer.domain.condition.*;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;

import java.time.LocalDateTime;

public class ForecastFixture {

    public static ForecastBuilder builder() {
        return new ForecastBuilder();
    }

    public static Forecast createDefault() {
        return builder().build();
    }

    public static class ForecastBuilder {
        private long id = 0L;
        private LocalDateTime dateTime = LocalDateTime.of(2025, 8, 1, 00, 0, 0);
        private ForecastType forecastType =  ForecastType.SHORT;
        private Sky sky = Sky.CLOUDY;
        private double temperature = 20.5;
        private int humidity = 60;
        private WindDirection windDir = WindDirection.N;
        private double windSpeed = 3.5;
        private PrecipitationType precipitationType = PrecipitationType.NONE;
        private String precipitation = "1.5mm";
        private double precipitationProbability = 10;
        private String snowAccumulation = "2.0mm";
        private DailyTemperature dailyTemperature = DailyTemperatureFixture.createDefault();

        public ForecastBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ForecastBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ForecastBuilder forecastType(ForecastType forecastType) {
            this.forecastType = forecastType;
            return this;
        }

        public ForecastBuilder sky(Sky sky) {
            this.sky = sky;
            return this;
        }

        public ForecastBuilder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public ForecastBuilder humidity(int humidity) {
            this.humidity = humidity;
            return this;
        }

        public ForecastBuilder windDir(WindDirection windDir) {
            this.windDir = windDir;
            return this;
        }

        public ForecastBuilder windSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public ForecastBuilder precipitationType(PrecipitationType precipitationType) {
            this.precipitationType = precipitationType;
            return this;
        }

        public ForecastBuilder precipitation(String precipitation) {
            this.precipitation = precipitation;
            return this;
        }

        public ForecastBuilder precipitationProbability(double precipitationProbability) {
            this.precipitationProbability = precipitationProbability;
            return this;
        }

        public ForecastBuilder snowAccumulation(String snowAccumulation) {
            this.snowAccumulation = snowAccumulation;
            return this;
        }
        public ForecastBuilder dailyTemperature(DailyTemperature dailyTemperature) {
            this.dailyTemperature = dailyTemperature;
            return this;
        }


        public Forecast build() {
            return new Forecast(
                    id,
                    dateTime,
                    forecastType,
                    new SkyCondition(sky),
                    new TemperatureCondition(temperature, windSpeed, humidity),
                    new HumidityCondition(humidity),
                    new WindCondition(windDir, windSpeed),
                    new PrecipitationCondition(precipitationType, precipitation, precipitationProbability, snowAccumulation),
                    dailyTemperature
            );
        }
    }
}