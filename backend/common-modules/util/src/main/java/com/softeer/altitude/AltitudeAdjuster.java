package com.softeer.altitude;

import com.softeer.altitude.transform.TemperatureAltitudeAdjustment;
import com.softeer.altitude.transform.WindSpeedAltitudeAdjustment;

public final class AltitudeAdjuster {

    private static final AltitudeAdjust temperatureTransformer = new TemperatureAltitudeAdjustment();
    private static final AltitudeAdjust windSpeedTransformer = new WindSpeedAltitudeAdjustment();

    private AltitudeAdjuster() {}

    public static double adjustTemperature(double temperature, double courseAltitude, double mountainAltitude) {
        return temperatureTransformer.adjust(temperature, courseAltitude, mountainAltitude);
    }

    public static double adjustWindSpeed(double windSpeed, double courseAltitude, double mountainAltitude) {
        return windSpeedTransformer.adjust(windSpeed, courseAltitude, mountainAltitude);
    }
}