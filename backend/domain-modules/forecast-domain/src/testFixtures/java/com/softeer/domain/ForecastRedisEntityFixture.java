package com.softeer.domain;

import com.softeer.entity.ForecastRedisEntity;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;

import java.time.LocalDateTime;

public class ForecastRedisEntityFixture {

    public static ForecastRedisEntity createDefault() {
        return builder().build();
    }

    public static ForecastRedisEntityBuilder builder() {
        return new ForecastRedisEntityBuilder();
    }

    public static class ForecastRedisEntityBuilder {
        private long id = 1L;
        private LocalDateTime dateTime = LocalDateTime.of(2025, 8, 17, 12, 0);
        private ForecastType forecastType = ForecastType.SHORT;
        private Sky sky = Sky.SUNNY;
        private double temperature = 22.5;
        private double humidity = 55.0;
        private WindDirection windDir = WindDirection.ENE;
        private double windSpeed = 3.2;
        private PrecipitationType precipitationType = PrecipitationType.RAIN;
        private String precipitation = "1mm";
        private double precipitationProbability = 40.0;
        private String snowAccumulation = "0cm";
        private double highestTemperature = 25.5;
        private double lowestTemperature = 18.2;
        private int gridId = 1;

        public ForecastRedisEntityBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ForecastRedisEntityBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ForecastRedisEntityBuilder forecastType(ForecastType forecastType) {
            this.forecastType = forecastType;
            return this;
        }

        public ForecastRedisEntityBuilder sky(Sky sky) {
            this.sky = sky;
            return this;
        }

        public ForecastRedisEntityBuilder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public ForecastRedisEntityBuilder humidity(double humidity) {
            this.humidity = humidity;
            return this;
        }

        public ForecastRedisEntityBuilder windDir(WindDirection windDir) {
            this.windDir = windDir;
            return this;
        }

        public ForecastRedisEntityBuilder windSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public ForecastRedisEntityBuilder precipitationType(PrecipitationType precipitationType) {
            this.precipitationType = precipitationType;
            return this;
        }

        public ForecastRedisEntityBuilder precipitation(String precipitation) {
            this.precipitation = precipitation;
            return this;
        }

        public ForecastRedisEntityBuilder precipitationProbability(double probability) {
            this.precipitationProbability = probability;
            return this;
        }

        public ForecastRedisEntityBuilder snowAccumulation(String snowAccumulation) {
            this.snowAccumulation = snowAccumulation;
            return this;
        }

        public ForecastRedisEntityBuilder highestTemperature(double highestTemperature) {
            this.highestTemperature = highestTemperature;
            return this;
        }

        public ForecastRedisEntityBuilder lowestTemperature(double lowestTemperature) {
            this.lowestTemperature = lowestTemperature;
            return this;
        }

        public ForecastRedisEntityBuilder gridId(int gridId) {
            this.gridId = gridId;
            return this;
        }

        public ForecastRedisEntity build() {
            return new ForecastRedisEntity(
                    id,
                    dateTime,
                    forecastType,
                    sky,
                    temperature,
                    humidity,
                    windDir,
                    windSpeed,
                    precipitationType,
                    precipitation,
                    precipitationProbability,
                    snowAccumulation,
                    highestTemperature,
                    lowestTemperature,
                    gridId
            );
        }
    }
}
