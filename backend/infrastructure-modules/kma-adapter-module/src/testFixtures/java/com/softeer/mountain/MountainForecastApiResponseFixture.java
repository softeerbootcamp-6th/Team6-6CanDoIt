package com.softeer.mountain;

import com.softeer.mountain.dto.MountainForecastApiResponse;

public final class MountainForecastApiResponseFixture {

    private MountainForecastApiResponseFixture() {}

    public static MountainForecastApiResponseBuilder builder() {
        return new MountainForecastApiResponseBuilder();
    }

    public static MountainForecastApiResponse createDefault() {
        return builder().build();
    }

    public static class MountainForecastApiResponseBuilder {
        private String baseDate = "20250814";
        private String baseTime = "1300";
        private String category = "POP";
        private String forecastDate = "20250814";
        private String forecastTime = "1400";
        private String forecastValue = "50";
        private int nx = 60;
        private int ny = 127;
        private String lat = "37.5665";
        private String lon = "126.9780";
        private String alt = "500";
        private String stationName = "설악산";

        public MountainForecastApiResponseBuilder baseDate(String baseDate) {
            this.baseDate = baseDate;
            return this;
        }

        public MountainForecastApiResponseBuilder baseTime(String baseTime) {
            this.baseTime = baseTime;
            return this;
        }

        public MountainForecastApiResponseBuilder category(String category) {
            this.category = category;
            return this;
        }

        public MountainForecastApiResponseBuilder forecastDate(String forecastDate) {
            this.forecastDate = forecastDate;
            return this;
        }

        public MountainForecastApiResponseBuilder forecastTime(String forecastTime) {
            this.forecastTime = forecastTime;
            return this;
        }

        public MountainForecastApiResponseBuilder forecastValue(String forecastValue) {
            this.forecastValue = forecastValue;
            return this;
        }

        public MountainForecastApiResponseBuilder nx(int nx) {
            this.nx = nx;
            return this;
        }

        public MountainForecastApiResponseBuilder ny(int ny) {
            this.ny = ny;
            return this;
        }

        public MountainForecastApiResponseBuilder lat(String lat) {
            this.lat = lat;
            return this;
        }

        public MountainForecastApiResponseBuilder lon(String lon) {
            this.lon = lon;
            return this;
        }

        public MountainForecastApiResponseBuilder alt(String alt) {
            this.alt = alt;
            return this;
        }

        public MountainForecastApiResponseBuilder stationName(String stationName) {
            this.stationName = stationName;
            return this;
        }

        public MountainForecastApiResponse build() {
            return new MountainForecastApiResponse(
                    baseDate,
                    baseTime,
                    category,
                    forecastDate,
                    forecastTime,
                    forecastValue,
                    nx,
                    ny,
                    lat,
                    lon,
                    alt,
                    stationName
            );
        }
    }
}
