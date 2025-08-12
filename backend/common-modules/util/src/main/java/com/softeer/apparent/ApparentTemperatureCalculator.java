package com.softeer.apparent;

public final class ApparentTemperatureCalculator {

    private ApparentTemperatureCalculator() {}

    /**
     * 체감온도 계산 메서드
     *
     * @param temperature        실제 기온 (°C)
     * @param windSpeed          풍속 (m/s)
     * @param relativeHumidity   상대습도 (%)
     * @return                   체감온도 (°C)
     */
    public static double calculateApparentTemperature(double temperature, double windSpeed, double relativeHumidity) {
        double apparentTemperature;
        if (temperature <= 10.0) {
            apparentTemperature = calculateWinterApparentTemperature(temperature, windSpeed);
        } else {
            apparentTemperature =  calculateSummerApparentTemperature(temperature, relativeHumidity);
        }
        return Math.round(apparentTemperature * 10.0) / 10.0;
    }

    /**
     * 겨울철 체감온도 계산 (Wind Chill Index)
     * 단위: temperature(°C), windSpeed(m/s)
     */
    private static double calculateWinterApparentTemperature(double temperature, double windSpeed) {
        double windSpeedKmPerHour = windSpeed * 3.6; // m/s → km/h
        double windFactor = Math.pow(windSpeedKmPerHour, 0.16);
        return 13.12 + 0.6215 * temperature - 11.37 * windFactor + 0.3965 * windFactor * temperature;
    }

    /**
     * 여름철 체감온도 계산 (Heat Index using wet bulb temperature)
     * 단위: temperature(°C), relativeHumidity(%)
     */
    private static double calculateSummerApparentTemperature(double temperature, double relativeHumidity) {
        double wetBulbTemperature = calculateWetBulbTemperature(temperature, relativeHumidity);
        return -0.2442 + 0.55399 * wetBulbTemperature
                + 0.45535 * temperature
                - 0.0022 * Math.pow(wetBulbTemperature, 2)
                + 0.00278 * wetBulbTemperature * temperature
                + 3.0;
    }

    /**
     * 습구온도 계산 (Stull 추정식)
     * 단위: temperature(°C), relativeHumidity(%)
     */
    private static double calculateWetBulbTemperature(double temperature, double relativeHumidity) {
        double sqrtHumidity = Math.sqrt(relativeHumidity + 8.313659);
        double humidityPower = Math.pow(relativeHumidity, 1.5); // RH^(3/2)

        double term1 = temperature * Math.atan(0.151977 * sqrtHumidity);
        double term2 = Math.atan(temperature + relativeHumidity);
        double term3 = Math.atan(relativeHumidity - 1.67633);
        double term4 = 0.00391838 * humidityPower * Math.atan(0.023101 * relativeHumidity);

        return term1 + term2 - term3 + term4 - 4.686035;
    }
}
