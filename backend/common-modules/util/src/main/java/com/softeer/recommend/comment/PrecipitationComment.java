package com.softeer.recommend.comment;

import com.softeer.condition.Condition;
import com.softeer.condition.Literal;
import com.softeer.condition.MillimeterRange;
import com.softeer.recommend.Comment;
import com.softeer.recommend.TotalLevel;

import java.util.Arrays;
import java.util.List;

import static com.softeer.recommend.MeasurementLevel.*;
import static com.softeer.recommend.TypeLevel.*;

public enum PrecipitationComment implements Comment {


    LEVEL_0(new TotalLevel(LEVEL_ZERO, PRECIPITATION), new Literal("강수없음"), "등산하기 좋은 날씨에요."),
    LEVEL_1(new TotalLevel(LEVEL_ONE, PRECIPITATION), new Literal("1mm 미만"), "등산하기 좋은 날씨에요."),
    LEVEL_2(new TotalLevel(LEVEL_TWO, PRECIPITATION), new MillimeterRange(1.0, 1.5), "바람막이를 챙겨가세요."),
    LEVEL_3(new TotalLevel(LEVEL_THREE, PRECIPITATION), new MillimeterRange(1.5, 3), "바람이 강하게 부니 등산 시 유의해주세요."),
    LEVEL_4(new TotalLevel(LEVEL_FOUR, PRECIPITATION), PrecipitationComment::alwaysTrue, "오늘은 등산을 강력히 자제해 주세요."),
    ;

    private static final List<PrecipitationComment> precipitationComments = Arrays.asList(values());

    private final TotalLevel totalLevel;
    private final Condition condition;
    private final String comment;

    PrecipitationComment(TotalLevel totalLevel, Condition condition, String comment) {
        this.totalLevel = totalLevel;
        this.condition = condition;
        this.comment = comment;
    }

    private static boolean alwaysTrue(String precipitation) {
        return true;
    }


    private boolean isInRange(String precipitation) {
        return condition.matches(precipitation);
    }

    public static PrecipitationComment findPrecipitationComment(String precipitation) {
        for (PrecipitationComment precipitationComment : precipitationComments) {
            if(precipitationComment.isInRange(precipitation)) return precipitationComment;
        }

        throw new IllegalArgumentException("정의되지 않은 풍속 범위: " + precipitation);
    }

    @Override
    public TotalLevel totalLevel() {
        return totalLevel;
    }

    @Override
    public String comment() {
        return comment;
    }
}
