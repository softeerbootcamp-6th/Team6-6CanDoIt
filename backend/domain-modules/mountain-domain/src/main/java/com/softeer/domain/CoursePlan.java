package com.softeer.domain;

import com.softeer.error.ExceptionCreator;
import com.softeer.exception.CoursePlanException;

import java.time.LocalDate;

public record CoursePlan(
        Course course,
        Mountain mountain,
        SunTime sunTime,
        LocalDate date
) {

    public CoursePlan {
        require(course,   "Course");
        require(mountain, "Mountain");
        require(sunTime,  "Sun-time");
        require(date,     "Date");
    }

    private static void require(Object value, String fieldName) {
        if (value == null) {
            throw ExceptionCreator.create(
                    CoursePlanException.COURSE_PLAN_INTERNAL_ERROR,
                    fieldName + " is required");
        }
    }
}
