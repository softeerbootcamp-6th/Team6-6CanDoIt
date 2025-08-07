package com.softeer.condition;

public record Literal(String value) implements Condition {
    @Override
    public boolean matches(String input) {
        return value.equals(input);
    }
}

