package com.softeer.altitude.transform;

import com.softeer.altitude.AltitudeAdjust;

public class TemperatureAltitudeAdjustment implements AltitudeAdjust {

    private static final double TEMPERATURE_DECREASE_RATE = 0.65;
    private static final double ALTITUDE_INTERVAL = 100.0;

    @Override
    public double adjust(double temperature, double courseAltitude, double mountainAltitude) {
        if (courseAltitude == mountainAltitude) {
            return temperature;
        }

        double altitudeDifference = mountainAltitude - courseAltitude;
        double totalCorrection = (altitudeDifference / ALTITUDE_INTERVAL) * TEMPERATURE_DECREASE_RATE;

        return temperature + totalCorrection;
    }
}
