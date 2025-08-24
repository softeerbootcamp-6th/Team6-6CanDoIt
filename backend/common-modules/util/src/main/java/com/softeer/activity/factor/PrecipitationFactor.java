package com.softeer.activity.factor;

import com.softeer.condition.Condition;
import com.softeer.condition.Literal;
import com.softeer.condition.MillimeterRange;

import java.util.Arrays;
import java.util.List;

public enum PrecipitationFactor {

    NO_RAIN   (4, List.of(new Literal("강수없음"))),
    DRIZZLE   (3, List.of(new Literal("1mm 미만"), new Literal("1.0mm 미만"))),
    LIGHT     (3, List.of(new MillimeterRange(1.0, 1.3))),
    MODERATE  (2, List.of(new MillimeterRange(1.3, 2.5))),
    HEAVY     (1, List.of(new MillimeterRange(2.5, 7.6))),
    DOWNPOUR  (1, List.of(new MillimeterRange(7.6, 30.0))),
    VERY_HEAVY(1, List.of(new Literal("30.0~50.0mm"),  new Literal("30~50mm"), new Literal("30.0mm~50mm"), new Literal("30.0mm~50mm"))),
    EXTREME   (1, List.of(new Literal("50.0mm 이상"), new Literal("50mm 이상")));

    private static final List<PrecipitationFactor> precipitationFactors = Arrays.asList(values());

    private final int score;
    private final List<Condition> conditions;

    PrecipitationFactor(int score, List<Condition> conditions) {
        this.score      = score;
        this.conditions = conditions;
    }

    private boolean matches(String precipitation) {
        return conditions.stream().anyMatch(condition -> condition.matches(precipitation));
    }

    public static int calculateFactor(String precipitation) {
        for (PrecipitationFactor factor : precipitationFactors) {
            if(factor.matches(precipitation)) return factor.score;
        }

        throw new IllegalArgumentException("정의되지 않은 강수량 범위: " + precipitation);
    }
}