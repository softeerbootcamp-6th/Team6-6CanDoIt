package com.softeer.domain;

public record Mountain(
        long id,
        String name,
        int altitude,
        String imageUrl,
        String description
) {
}
