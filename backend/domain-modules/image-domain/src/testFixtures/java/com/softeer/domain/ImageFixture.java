package com.softeer.domain;

import java.time.LocalDateTime;

public class ImageFixture {

    public static ImageFixtureBuilder builder() {
        return new ImageFixtureBuilder();
    }

    public static Image createDefault() {
        return builder().build();
    }

    public static class ImageFixtureBuilder {
        private long id = 0L;
        private String imageUrl;
        private LocalDateTime createdAt = LocalDateTime.now();

        public ImageFixtureBuilder id(long id) {
            this.id = id;
            return this;
        }

        public ImageFixtureBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ImageFixtureBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Image build() {
            return new Image(id, imageUrl, createdAt);
        }
    }
}
