package com.softeer.domain;

import java.time.LocalTime;

public class SunTimeFixture {

    public static SunTimeFixtureBuilder builder() {
        return new SunTimeFixtureBuilder();
    }

    public static SunTime createDefault() {
        return builder().build();
    }

    public static class SunTimeFixtureBuilder {
        private LocalTime sunrise = LocalTime.of(6, 0);
        private LocalTime sunset = LocalTime.of(18, 0);

        public SunTimeFixtureBuilder sunrise(LocalTime sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        public SunTimeFixtureBuilder sunset(LocalTime sunset) {
            this.sunset = sunset;
            return this;
        }

        public SunTime build() {
            return new SunTime(sunrise, sunset);
        }
    }
}
