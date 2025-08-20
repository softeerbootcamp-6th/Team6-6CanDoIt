package com.softeer.shortterm;

import com.softeer.shortterm.dto.response.ShortForecastItem;

import java.time.LocalDate;
import java.time.LocalTime;

public final class ShortForecastItemFixture {

    private ShortForecastItemFixture() {}

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static ShortForecastItem createDefault() {
        return builder().build();
    }

    public static class ItemBuilder {
        private String baseDate = "20250816";
        private String baseTime = "0500";
        private String category = "TMP"; // 기온(TMP)을 기본값으로 설정
        private LocalDate forecastDate = LocalDate.of(2025, 8, 20);
        private LocalTime forecastTime = LocalTime.of(6, 0);
        private String forecastValue = "22";
        private int nx = 60;
        private int ny = 127;

        public ItemBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ItemBuilder forecastValue(String forecastValue) {
            this.forecastValue = forecastValue;
            return this;
        }

        public ItemBuilder forecastTime(LocalTime forecastTime) {
            this.forecastTime = forecastTime;
            return this;
        }

        public ShortForecastItem build() {
            return new ShortForecastItem(
                    baseDate,
                    baseTime,
                    category,
                    forecastDate,
                    forecastTime,
                    forecastValue,
                    nx,
                    ny
            );
        }
    }
}
