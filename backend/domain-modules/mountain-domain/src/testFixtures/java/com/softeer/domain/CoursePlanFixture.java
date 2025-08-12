package com.softeer.domain;

import java.time.LocalDate;

public class CoursePlanFixture {

    public static CoursePlanFixtureBuilder builder() {
        return new CoursePlanFixtureBuilder();
    }

    public static CoursePlan createDefault() {
        return builder().build();
    }

    public static class CoursePlanFixtureBuilder {
        private Mountain mountain = MountainFixture.createDefault();
        private Course course = CourseFixture.createDefault();
        private SunTime sunTime = SunTimeFixture.createDefault();
        private LocalDate localDate = LocalDate.of(2025, 8, 10);

        public CoursePlanFixtureBuilder mountain(Mountain mountain) {
            this.mountain = mountain;
            return this;
        }

        public CoursePlanFixtureBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public CoursePlanFixtureBuilder sunTime(SunTime sunTime) {
            this.sunTime = sunTime;
            return this;
        }

        public CoursePlanFixtureBuilder localDate(LocalDate localDate) {
            this.localDate = localDate;
            return this;
        }

        public CoursePlan build() {
            return new CoursePlan(course, mountain, sunTime, localDate);
        }
    }
}
