package com.softeer.altitude.transform;

import com.softeer.altitude.AltitudeAdjust;

public class WindSpeedAltitudeAdjustment implements AltitudeAdjust {

    private static final double WIND_SHEAR_EXPONENT = 0.25;

    @Override
    public double adjust(double windSpeed, double courseAltitude, double mountainAltitude) {
        if (courseAltitude == mountainAltitude) {
            return windSpeed;
        }

        double ratio = courseAltitude / mountainAltitude;

        double value = windSpeed * Math.pow(ratio, WIND_SHEAR_EXPONENT);
        return Math.round(value * 10.0) / 10.0;
    }
}