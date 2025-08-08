package com.softeer.activity;

import com.softeer.activity.factor.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public final class HikingActivityCalculator {

    private static final double A1 = 0.2434;
    private static final double A2 = 0.1798;
    private static final double A3 = 0.2882;
    private static final double A4 = 0.1589;
    private static final double A5 = 0.1297;

    private HikingActivityCalculator() {
    }

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
            String precipitation,
            double windSpeed,
            double humidity
    ) {
        int temperatureFactor = TemperatureFactor.calculateFactor(temperature);
        int dailyTemperatureRangeFactor = DailyTemperatureRangeFactor.calculateFactor(dailyTemperatureRange);
        int precipitationFactor = PrecipitationFactor.calculateFactor(precipitation);
        int windSpeedFactor = WindSpeedFactor.calculateFactor(windSpeed);
        int humidityFactor = HumidityFactor.calculateFactor(humidity);

        System.out.println("temperatureFactor: " + temperatureFactor);
        System.out.println("dailyTemperatureRangeFactor: " + dailyTemperatureRangeFactor);
        System.out.println("precipitationFactor: " + precipitationFactor);
        System.out.println("windSpeedFactor: " + windSpeedFactor);
        System.out.println("humidityFactor: " + humidityFactor);

        double calculatedValue = calculate(temperatureFactor, dailyTemperatureRangeFactor, precipitationFactor, windSpeedFactor, humidityFactor);

        BigDecimal bigDecimal = new BigDecimal(calculatedValue);
        BigDecimal roundedValue = bigDecimal.setScale(4, RoundingMode.HALF_UP);
        double finalValue = roundedValue.doubleValue();

        return HikingActivityStatus.description(finalValue);
    }

    /**
     * 산악활동지수 (C.I)를 계산합니다.
     * C.I = (a1 * T) + (a2 * DTR) + (a3 * P) + (a4 * W) + (a5 * Q)
     *
     * @param temperatureFactor               온도 [°C]
     * @param dailyTemperatureFactor   일교차 [°C]
     * @param precipitationFactor             강수량 [mm]
     * @param windSpeedFactor                 풍속 [m/s]
     * @param humidityFactor                  습도 [%]
     * @return 계산된 산악활동지수 값
     */
    private static double calculate(
            int temperatureFactor,
            int dailyTemperatureFactor,
            int precipitationFactor,
            int windSpeedFactor,
            int humidityFactor
    ) {
        return (A1 * temperatureFactor) + (A2 * dailyTemperatureFactor) + (A3 * precipitationFactor) + (A4 * windSpeedFactor) + (A5 * humidityFactor);
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