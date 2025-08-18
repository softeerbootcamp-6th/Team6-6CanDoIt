package com.softeer.batch.mapper.impl;

import com.softeer.batch.mapper.infra.CodeMapper;
import com.softeer.entity.enums.WindDirection;

public class DegreeWindDirectionMapper implements CodeMapper<WindDirection> {

    @Override
    public WindDirection map(String raw) {
        double deg = Double.parseDouble(raw);

        if (deg >= 348.75 || deg < 11.25) return WindDirection.N;
        if (deg < 33.75) return WindDirection.NNE;
        if (deg < 56.25) return WindDirection.NE;
        if (deg < 78.75) return WindDirection.ENE;
        if (deg < 101.25) return WindDirection.E;
        if (deg < 123.75) return WindDirection.ESE;
        if (deg < 146.25) return WindDirection.SE;
        if (deg < 168.75) return WindDirection.SSE;
        if (deg < 191.25) return WindDirection.S;
        if (deg < 213.75) return WindDirection.SSW;
        if (deg < 236.25) return WindDirection.SW;
        if (deg < 258.75) return WindDirection.WSW;
        if (deg < 281.25) return WindDirection.W;
        if (deg < 303.75) return WindDirection.WNW;
        if (deg < 326.25) return WindDirection.NW;
        if (deg < 348.75) return WindDirection.NNW;

        return WindDirection.N;
    }
}
