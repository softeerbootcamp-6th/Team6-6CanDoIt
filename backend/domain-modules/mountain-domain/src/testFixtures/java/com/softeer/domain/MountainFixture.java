package com.softeer.domain;

public class MountainFixture {

    public static MountainBuilder builder() {
        return new MountainBuilder();
    }

    public static Mountain createDefault() {
        return builder().build();
    }

    public static class MountainBuilder {
        private long id= 1L;
        private String name = "test";
        private int altitude = 999;
        private String imageUrl = "www.test.com";
        private String description = "test description";
        private Grid grid = new Grid(1, 1, 1);

        public MountainBuilder id(long id) {
            this.id = id;
            return this;
        }

        public MountainBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MountainBuilder altitude(int altitude) {
            this.altitude = altitude;
            return this;
        }

        public MountainBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public MountainBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MountainBuilder grid(Grid grid) {
            this.grid = grid;
            return this;
        }

        public Mountain build() {
            return new Mountain(
                    id,
                    name,
                    altitude,
                    imageUrl,
                    description,
                    grid
            );
        }
    }
}
