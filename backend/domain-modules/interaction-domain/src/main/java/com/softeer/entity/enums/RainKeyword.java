package com.softeer.entity.enums;

public enum RainKeyword implements KeywordInterface {
    LIGHT("부슬비가 내려요"),
    HEAVY("장대비가 쏟아져요"),
    THUNDER("천둥 번개가 쳐요"),
    DOWNPOUR("폭우가 내려요");

    private final String description;

    RainKeyword(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
