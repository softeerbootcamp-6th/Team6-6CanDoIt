package com.softeer.domain;

import com.softeer.entity.enums.Level;

import java.time.LocalTime;

public class CourseFixture {

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    public static Course createDefault() {
        return builder().build();
    }

    public static class CourseBuilder {

        private long id = 1L;
        private String name = "test course name";
        private double totalDistance = 9.9;
        private int totalDuration = 123;
        private Level level = Level.EASY;
        private LocalTime sunrise = LocalTime.of(7, 0);
        private LocalTime sunset = LocalTime.of(19, 0);
        private String mountainName = "test mountain name";

        public CourseBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder totalDistance(double km) {
            this.totalDistance = km;
            return this;
        }

        public CourseBuilder totalDuration(int minutes) {
            this.totalDuration = minutes;
            return this;
        }

        public CourseBuilder level(Level level) {
            this.level = level;
            return this;
        }

        public CourseBuilder sunrise(LocalTime sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        public CourseBuilder sunset(LocalTime sunset) {
            this.sunset = sunset;
            return this;
        }

        public CourseBuilder mountainName(String mountainName) {
            this.mountainName = mountainName;
            return this;
        }

        public Course build() {
            return new Course(
                    id,
                    name,
                    totalDistance,
                    totalDuration,
                    level,
                    sunrise,
                    sunset,
                    mountainName
            );
        }
    }
}
