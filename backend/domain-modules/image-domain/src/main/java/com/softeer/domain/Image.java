package com.softeer.domain;

import java.time.LocalDateTime;

public record Image(long id, String imageUrl, LocalDateTime createdAt) {
}
