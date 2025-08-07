package com.softeer.activity.factor;

import com.softeer.condition.Condition;
import com.softeer.condition.Literal;
import com.softeer.condition.Range;

import java.util.Arrays;
import java.util.List;

public enum PrecipitationFactor  {

    NO_RAIN   (4, new Literal("강수없음")),
    DRIZZLE   (3, new Literal("1mm 미만")),
    LIGHT     (3, new Range(1.0, 1.3)),
    MODERATE  (2, new Range(1.3, 2.5)),
    HEAVY     (1, new Range(2.5, 7.6)),
    DOWNPOUR  (1, new Range(7.6, 30.0)),
    VERY_HEAVY(1, new Literal("30.0~50.0mm")),
    EXTREME   (1, new Literal("50.0mm 이상"));

    private static final List<PrecipitationFactor> precipitationFactors = Arrays.asList(values());

    private final int score;
    private final Condition condition;

    PrecipitationFactor(int score, Condition condition) {
        this.score      = score;
        this.condition = condition;
    }

    private boolean matches(String precipitation) {
        return condition.matches(precipitation);
    }

    public static int calculateFactor(String precipitation) {
        for (PrecipitationFactor factor : precipitationFactors) {
            if(factor.matches(precipitation)) return factor.score;
        }

        throw new IllegalArgumentException("정의되지 않은 강수량 범위: " + precipitation);
    }
}
