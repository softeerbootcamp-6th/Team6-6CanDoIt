package com.softeer.entity.enums;

public enum EtceteraKeyword implements Keyword{

    FOGGY("안개가 껴요"),
    DUSTY("미세먼지가 많아요"),
    BLURRY("시야가 흐려요")
    ;

    private final String description;

    EtceteraKeyword(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
