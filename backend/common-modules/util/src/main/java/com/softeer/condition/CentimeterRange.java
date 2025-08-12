package com.softeer.condition;

import java.util.regex.Pattern;

public final class CentimeterRange extends AbstractRange {
    private static final Pattern P = Pattern.compile("^(\\d+(?:\\.\\d+)?)cm$");

    public CentimeterRange(double minInclusive, double maxExclusive) {
        super(minInclusive, maxExclusive);
    }

    @Override
    protected Pattern getPattern() {
        return P;
    }
}