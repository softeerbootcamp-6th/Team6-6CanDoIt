package com.softeer.domain.condition;

import com.softeer.domain.Displayable;
import com.softeer.entity.enums.Sky;

public record SkyCondition(Sky sky) implements Displayable {
    @Override
    public String displayDescription() {
        return sky.getDisplayDescription();
    }
}
