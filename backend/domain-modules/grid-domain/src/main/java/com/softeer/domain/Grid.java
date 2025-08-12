package com.softeer.domain;

public record Grid(int id, Coordinate coordinate) {

    public Grid(int id, int x, int y) {
        this(id, new Coordinate(x, y));
    }

    public int x() {
        return coordinate.x();
    }

    public int y() {
        return coordinate.y();
    }
}
