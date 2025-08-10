package com.softeer.recommend.comment;

import com.softeer.recommend.Comment;
import com.softeer.recommend.TotalLevel;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.softeer.recommend.MeasurementLevel.*;
import static com.softeer.recommend.TypeLevel.TEMPERATURE;

public enum TemperatureComment implements Comment {

    RISE_LEVEL_1(new TotalLevel(LEVEL_ONE, TEMPERATURE), temp -> temp >= 0 && temp <= 3, "등산하기 좋은 날씨에요."),
    RISE_LEVEL_2(new TotalLevel(LEVEL_TWO, TEMPERATURE), temp -> temp >= 4 && temp <= 6, "여러 겹의 옷을 입고 가시는 것을 추천드려요."),
    RISE_LEVEL_3(new TotalLevel(LEVEL_THREE, TEMPERATURE), temp -> temp >= 7 && temp <= 10, "더위에 주의하세요."),

    FALL_LEVEL_1(new TotalLevel(LEVEL_ONE, TEMPERATURE), temp -> temp >= -3 && temp < 0, "등산하기 좋은 날씨에요."),
    FALL_LEVEL_2(new TotalLevel(LEVEL_TWO, TEMPERATURE), temp -> temp >= -6 && temp <= -4, "여러 겹의 옷을 입고 가시는 것을 추천드려요."),
    FALL_LEVEL_3(new TotalLevel(LEVEL_THREE, TEMPERATURE), temp -> temp >= -10 && temp <= -7, "한파에 주의하세요."),

    LEVEL_4(new TotalLevel(LEVEL_FOUR, TEMPERATURE), temp -> true, "오늘은 등산을 강력히 자제해 주세요.");

    private static final List<TemperatureComment> temperatureComments = Arrays.asList(values());

    private final TotalLevel totalLevel;
    private final Predicate<Double> condition;
    private final String comment;

    TemperatureComment(TotalLevel totalLevel, Predicate<Double> condition, String comment) {
        this.totalLevel = totalLevel;
        this.condition = condition;
        this.comment = comment;
    }

    private boolean isInRange(double temperatureDiff) {
        return condition.test(temperatureDiff);
    }


    public static TemperatureComment findTemperatureComment(double temperatureDiff) {
        for (TemperatureComment temperatureComment : temperatureComments) {
            if(temperatureComment.isInRange(temperatureDiff)) return temperatureComment;
        }

        throw new IllegalArgumentException("정의되지 않은 온도 차 범위: " + temperatureDiff);
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