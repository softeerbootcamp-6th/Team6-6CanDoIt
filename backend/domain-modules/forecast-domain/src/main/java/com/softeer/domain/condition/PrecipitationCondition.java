package com.softeer.domain.condition;

import com.softeer.domain.Displayable;
import com.softeer.entity.enums.PrecipitationType;

public record PrecipitationCondition(PrecipitationType precipitationType, int precipitation,
                                     double precipitationProbability, double snowAccumulation) implements Displayable {
    @Override
    public String displayDescription() {
        if(precipitationType == PrecipitationType.NONE) return "0%";
        return precipitationType.name() + " " + precipitationProbability + "%";
    }
}