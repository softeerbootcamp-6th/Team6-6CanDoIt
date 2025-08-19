package com.softeer.domain;

import java.time.LocalDateTime;

public record CardHistory(long id, long userId, long courseId,
                          LocalDateTime forecastDate, LocalDateTime updatedAt
                          ) {
}
