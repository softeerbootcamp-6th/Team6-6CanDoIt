package com.softeer.dto.response;

import com.softeer.domain.Course;

public record CourseInfoResponse(long courseId, String courseName) {
    public CourseInfoResponse(Course course) {
        this(course.id(), course.name());
    }
}
