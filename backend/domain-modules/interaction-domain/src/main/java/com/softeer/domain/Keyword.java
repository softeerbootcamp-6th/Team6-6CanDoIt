package com.softeer.domain;

import com.softeer.entity.enums.KeywordInterface;

public record Keyword(int id, String description) {
    public Keyword(int id, KeywordInterface keyword) {
        this(id, keyword.getDescription());
    }
}
