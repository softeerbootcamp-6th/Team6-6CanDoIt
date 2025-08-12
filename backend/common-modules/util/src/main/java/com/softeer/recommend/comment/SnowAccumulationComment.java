package com.softeer.recommend.comment;

import com.softeer.condition.CentimeterRange;
import com.softeer.condition.Condition;
import com.softeer.condition.Literal;
import com.softeer.recommend.Comment;
import com.softeer.recommend.TotalLevel;

import java.util.Arrays;
import java.util.List;

import static com.softeer.recommend.MeasurementLevel.*;
import static com.softeer.recommend.TypeLevel.SNOW_ACCUMULATION;

public enum SnowAccumulationComment implements Comment {

    LEVEL_0(new TotalLevel(LEVEL_ZERO, SNOW_ACCUMULATION), new Literal("적설없음"), "등산하기 좋은 날씨에요."),
    LEVEL_1(new TotalLevel(LEVEL_ONE, SNOW_ACCUMULATION), new Literal("0.5cm 미만"), "눈이 내릴 수 있어요."),
    LEVEL_2(new TotalLevel(LEVEL_TWO, SNOW_ACCUMULATION), new CentimeterRange(0.5, 1.0), "눈이 쌓일 수 있어요."),
    LEVEL_3(new TotalLevel(LEVEL_THREE, SNOW_ACCUMULATION), new CentimeterRange(1.0, 3.0), "오늘은 등산을 추천드리지 않아요."),
    LEVEL_4(new TotalLevel(LEVEL_FOUR, SNOW_ACCUMULATION), SnowAccumulationComment::alwaysTrue, "오늘은 등산을 강력히 자제해 주세요.")
    ;

    private static final List<SnowAccumulationComment> snowAccumulationComments = Arrays.asList(values());

    private final TotalLevel totalLevel;
    private final Condition condition;
    private final String comment;


    SnowAccumulationComment(TotalLevel totalLevel, Condition condition, String comment) {
        this.totalLevel = totalLevel;
        this.condition = condition;
        this.comment = comment;
    }


    private static boolean alwaysTrue(String snowAccumulation) {
        return true;
    }


    private boolean isInRange(String snowAccumulation) {
        return condition.matches(snowAccumulation);
    }

    public static SnowAccumulationComment findsnowAccumulationComment(String snowAccumulation) {
        for (SnowAccumulationComment snowAccumulationComment : snowAccumulationComments) {
            if(snowAccumulationComment.isInRange(snowAccumulation)) return snowAccumulationComment;
        }

        throw new IllegalArgumentException("정의되지 않은 풍속 범위: " + snowAccumulation);
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
