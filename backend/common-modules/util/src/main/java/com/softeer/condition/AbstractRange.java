package com.softeer.condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRange implements Condition {
    protected final double minInclusive;
    protected final double maxExclusive;

    public AbstractRange(double minInclusive, double maxExclusive) {
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
    }

    protected abstract Pattern getPattern();

    @Override
    public boolean matches(String input) {
        Matcher m = getPattern().matcher(input);
        if (!m.matches()) return false;
        double value = Double.parseDouble(m.group(1));
        return value >= minInclusive && value < maxExclusive;
    }
}