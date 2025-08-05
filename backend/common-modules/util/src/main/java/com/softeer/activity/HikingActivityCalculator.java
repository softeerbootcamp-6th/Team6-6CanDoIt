package com.softeer.activity;

import java.util.Arrays;
import java.util.List;

public final class HikingActivityCalculator {

    private static final double A1 = 0.2434;
    private static final double A2 = 0.1798;
    private static final double A3 = 0.2882;
    private static final double A4 = 0.1589;
    private static final double A5 = 0.1297;


    /**
     * 산악활동지수 값을 계산 후 값에 따라 카테고리를 반환합니다.
     *
     * @param temperature               온도 [°C]
     * @param dailyTemperatureRange     일교차 [°C]
     * @param precipitation             강수량 [mm]
     * @param windSpeed                 풍속 [m/s]
     * @param humidity                  습도 [%]
     * @return 산악활동지수 카테고리 (예: "매우 좋음", "좋음" 등)
     */
    public static String calculateHikingActivity(
            double temperature,
            double dailyTemperatureRange,
            double precipitation,
            double windSpeed,
            double humidity
    ) {
        double calculatedValue = calculate(temperature, dailyTemperatureRange, precipitation, windSpeed, humidity);
        return HikingActivityStatus.description(calculatedValue);
    }

    /**
     * 산악활동지수 (C.I)를 계산합니다.
     * C.I = (a1 * T) + (a2 * DTR) + (a3 * P) + (a4 * W) + (a5 * Q)
     *
     * @param temperature               온도 [°C]
     * @param dailyTemperatureRange   일교차 [°C]
     * @param precipitation             강수량 [mm]
     * @param windSpeed                 풍속 [m/s]
     * @param humidity                  습도 [%]
     * @return 계산된 산악활동지수 값
     */
    private static double calculate(
            double temperature,
            double dailyTemperatureRange,
            double precipitation,
            double windSpeed,
            double humidity
    ) {
        return (A1 * temperature) + (A2 * dailyTemperatureRange) + (A3 * precipitation) + (A4 * windSpeed) + (A5 * humidity);
    }

    private enum HikingActivityStatus {

        BAD("나쁨", Double.NEGATIVE_INFINITY, 2.3938),
        SLIGHTLY_BAD("약간나쁨", 2.3939, 3.1240),
        GOOD("좋음", 3.1241, 3.6108),
        VERY_GOOD("매우 좋음", 3.6109, Double.POSITIVE_INFINITY);

        private static final List<HikingActivityStatus> allStatuses = Arrays.asList(values());

        private final String description;
        private final double lowerBound;
        private final double upperBound;

        HikingActivityStatus(String description, double lowerBound, double upperBound) {
            this.description = description;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        private boolean isInRange(double value) {
            return value >= lowerBound && value <= upperBound;
        }

        public static String description(double calculatedValue) {
            for (HikingActivityStatus status : allStatuses) {
                if (status.isInRange(calculatedValue)) {
                    return status.description;
                }
            }
            throw new IllegalArgumentException("유효하지 않은 산악활동지수 값입니다: " + calculatedValue);
        }
    }
}