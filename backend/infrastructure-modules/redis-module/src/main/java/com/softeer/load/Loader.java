package com.softeer.load;

@FunctionalInterface
public interface Loader<T> {
    T load();
}
