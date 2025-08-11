package com.softeer.domain;

import com.softeer.entity.enums.Level;

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
        private double totalDuration = 123;
        private int altitude = 1000;
        private Level level = Level.EASY;
        private String imageUrl = "imageUrl";
        private Grid startGrid = new Grid(1, 1, 1);
        private Grid destinationGrid = new Grid(999, 999, 999);

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

        public CourseBuilder totalDuration(double duration) {
            this.totalDuration = duration;
            return this;
        }

        public CourseBuilder altitude(int altitude) {
            this.altitude = altitude;
            return this;
        }

        public CourseBuilder level(Level level) {
            this.level = level;
            return this;
        }

        public CourseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public CourseBuilder startGrid(Grid startGrid) {
            this.startGrid = startGrid;
            return this;
        }

        public CourseBuilder destinationGrid(Grid destinationGrid) {
            this.destinationGrid = destinationGrid;
            return this;
        }

        public Course build() {
            return new Course(
                    id,
                    name,
                    totalDistance,
                    totalDuration,
                    altitude,
                    level,
                    imageUrl,
                    startGrid,
                    destinationGrid
            );
        }
    }
}
