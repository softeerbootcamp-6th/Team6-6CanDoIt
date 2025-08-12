package com.softeer.recommend.comment;

import com.softeer.recommend.Comment;
import com.softeer.recommend.TotalLevel;

import java.util.Arrays;
import java.util.List;

import static com.softeer.recommend.MeasurementLevel.*;
import static com.softeer.recommend.TypeLevel.*;

public enum WindSpeedComment implements Comment {

    LEVEL_1(new TotalLevel(LEVEL_ONE, WIND_SPEED), 0, 2.9, "등산하기 좋은 날씨에요."),
    LEVEL_2(new TotalLevel(LEVEL_TWO, WIND_SPEED), 3, 5.9, "바람막이를 챙겨가세요."),
    LEVEL_3(new TotalLevel(LEVEL_THREE, WIND_SPEED), 6, 13.9, "바람이 강하게 부니 등산 시 유의해주세요."),
    LEVEL_4(new TotalLevel(LEVEL_FOUR, WIND_SPEED), 14.0, Double.MAX_VALUE, "오늘은 등산을 강력히 자제해 주세요."),
    ;

    private static final List<WindSpeedComment> windSpeedComments = Arrays.asList(values());

    private final TotalLevel totalLevel;
    private final double minInclusive;
    private final double maxExclusive;
    private final String comment;

    WindSpeedComment(TotalLevel totalLevel, double minInclusive, double maxExclusive, String comment) {
        this.totalLevel = totalLevel;
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
        this.comment = comment;
    }

    private boolean isInRange(double windSpeed) {
        return windSpeed >= minInclusive && windSpeed <= maxExclusive;
    }

    public static WindSpeedComment findWindSpeedComment(double windSpeed) {
        for (WindSpeedComment windSpeedComment : windSpeedComments) {
            if(windSpeedComment.isInRange(windSpeed)) return windSpeedComment;
        }

        throw new IllegalArgumentException("정의되지 않은 풍속 범위: " + windSpeed);
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
