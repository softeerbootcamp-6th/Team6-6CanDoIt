package com.softeer.domain;

public record ImageMeta(
        byte[] imageBytes,
        String fileName,
        String contentType
) {
}
