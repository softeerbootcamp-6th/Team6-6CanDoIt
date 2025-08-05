package com.softeer.activity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class HikingActivityCalculatorTest {

    @ParameterizedTest(name = "온도: {0}°, 일교차: {1}, 강수량: {2}mm, 풍속: {3}m/s, 습도: {4}% 일 때 {5}를 반환해야 한다.")
    @MethodSource("provideHikingActivityInputs")
    void shouldReturnCorrectCategoryForVariousInputs(
            double temperature,
            double dailyTemperatureRange,
            double precipitation,
            double windSpeed,
            double humidity,
            String expected
    ) {
        // When
        String result = HikingActivityCalculator.calculateHikingActivity(temperature, dailyTemperatureRange, precipitation, windSpeed, humidity);

        // Then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest(name = "온도: {0}°, 일교차: {1}, 강수량: {2}mm, 풍속: {3}m/s, 습도: {4}% 일 때 예외를 반환해야 합니다.")
    @MethodSource("provideInvalidHikingActivityInputs")
    void shouldThrowExceptionForInvalidInputs(
            double temperature,
            double dailyTemperatureRange,
            double precipitation,
            double windSpeed,
            double humidity
    ) {
        // When & Then

        Assertions.assertThatThrownBy(() -> 
                HikingActivityCalculator.calculateHikingActivity(temperature, dailyTemperatureRange, precipitation, windSpeed, humidity
        )).isEqualTo(RuntimeException.class);
    }

    private static Stream<Arguments> provideInvalidHikingActivityInputs() {
        return Stream.of(
                Arguments.of(Double.NaN, 5.0, 0.0, 2.0, 50.0), // 온도가 NaN일 때
                Arguments.of(10.0, Double.NaN, 0.0, 2.0, 50.0), // 일교차가 NaN일 때
                Arguments.of(10.0, 5.0, Double.NaN, 2.0, 50.0), // 강수량이 NaN일 때
                Arguments.of(10.0, 5.0, 0.0, Double.NaN, 50.0), // 풍속이 NaN일 때
                Arguments.of(10.0, 5.0, 0.0, 2.0, Double.NaN)  // 습도가 NaN일 때
        );
    }

    private static Stream<Arguments> provideHikingActivityInputs() {
        return Stream.of(
                Arguments.of(10.0, 5.0, 0.0, 2.0, 50.0, "매우 좋음"), // 인덱스: ~10.13
                Arguments.of(20.0, 8.0, 1.0, 5.0, 60.0, "매우 좋음"), // 인덱스: ~15.17
                Arguments.of(15.0, 5.0, 1.0, 3.0, 40.0, "매우 좋음"), // 인덱스: ~10.50 (수정됨)
                Arguments.of(10.0, 3.0, 0.0, 1.0, 20.0, "매우 좋음"), // 인덱스: ~5.72 (수정됨)
                Arguments.of(5.0, 2.0, 0.0, 0.5, 10.0, "약간나쁨"),   // 인덱스: ~2.95
                Arguments.of(0.0, 0.0, 0.0, 0.0, 10.0, "나쁨"),      // 인덱스: ~1.29 (수정됨)
                Arguments.of(0.0, 0.0, 0.0, 0.0, 1.0, "나쁨"),      // 인덱스: ~0.12
                Arguments.of(-10.0, 2.0, 0.0, 0.0, 0.0, "나쁨"),    // 인덱스: ~-2.07
                Arguments.of(0.0, 0.0, 0.0, 0.0, 0.0, "나쁨")
        );
    }
}