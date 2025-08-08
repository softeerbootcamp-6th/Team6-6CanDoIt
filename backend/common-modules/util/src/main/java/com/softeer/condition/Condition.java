package com.softeer.condition;

public sealed interface Condition permits Literal, Range {
    boolean matches(String input);
}