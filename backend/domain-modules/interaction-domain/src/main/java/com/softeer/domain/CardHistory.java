package com.softeer.domain;

import java.time.LocalDateTime;

public record CardHistory(long id, long courseId,
                          String mountainName, String courseName,
                          LocalDateTime forecastDate, LocalDateTime updatedAt
                          ) {
}
