package com.softeer.condition;

import java.util.regex.Pattern;

public final class MillimeterRange extends AbstractRange {
    private static final Pattern P = Pattern.compile("^(\\d+(?:\\.\\d+)?)mm$");

    public MillimeterRange(double minInclusive, double maxExclusive) {
        super(minInclusive, maxExclusive);
    }

    @Override
    protected Pattern getPattern() {
        return P;
    }
}