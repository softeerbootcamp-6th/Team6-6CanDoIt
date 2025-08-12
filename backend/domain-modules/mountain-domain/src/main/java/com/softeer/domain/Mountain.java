package com.softeer.domain;

public record Mountain(
        long id,
        int code,
        String name,
        int altitude,
        String imageUrl,
        String description,
        Grid grid
) {
}
