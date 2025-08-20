package com.softeer.dto.response.card;

import com.softeer.domain.Course;
import com.softeer.domain.Mountain;
import com.softeer.domain.SunTime;

import java.time.LocalTime;

public record MountainCourseCardResponse(
        long mountainId,
        String mountainName,
        String mountainImageUrl,
        long courseId,
        String courseName,
        String courseImageUrl,
        double distance,
        double duration,
        LocalTime sunrise,
        LocalTime sunset
) {
    public MountainCourseCardResponse(
            Mountain mountain,
            Course course,
            SunTime sunTime
    ) {
        this(
                mountain.id(),
                mountain.name(),
                mountain.imageUrl(),
                course.id(),
                course.name(),
                course.imageUrl(),
                course.totalDistance(),
                course.totalDuration(),
                sunTime.sunrise(),
                sunTime.sunset()
        );
    }
}
