package com.softeer.domain;

public class GridFixture {
    public static GridBuilder builder() {
        return new GridBuilder();
    }

    public static Grid createDefault() {
        return builder().build();
    }

    public static class GridBuilder {
        private int id = 0; // 기본값
        private int x = 55; // 기본값
        private int y = 127; // 기본값

        public GridBuilder id(int id) {
            this.id = id;
            return this;
        }

        public GridBuilder x(int x) {
            this.x = x;
            return this;
        }

        public GridBuilder y(int y) {
            this.y = y;
            return this;
        }

        public Grid build() {
            return new Grid(id, x, y);
        }
    }
}