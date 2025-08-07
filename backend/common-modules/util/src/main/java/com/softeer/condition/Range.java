package com.softeer.condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Range(double minInclusive, double maxExclusive) implements Condition {
    private static final Pattern P = Pattern.compile("^(\\d+(?:\\.\\d+)?)mm$");

    @Override
    public boolean matches(String input) {
        Matcher m = P.matcher(input);
        if (!m.matches()) return false;
        double mm = Double.parseDouble(m.group(1));
        return mm >= minInclusive && mm < maxExclusive;
    }
}