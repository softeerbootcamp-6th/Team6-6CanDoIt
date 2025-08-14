package com.softeer.mountain;

import com.softeer.mountain.dto.MountainForecastApiResponse;

/**
 * MountainForecastApiResponse 객체를 테스트용으로 생성해주는 픽스처 클래스.
 * private 생성자로 객체 생성을 막고, static 메소드만 제공합니다.
 */
public final class MountainForecastApiFixture {

    private MountainForecastApiFixture() {
        // 유틸리티 클래스이므로 인스턴스화 방지
    }

    /**
     * 기본값으로 채워진 '기온(TMP)' 예보 응답 객체를 생성합니다.
     * @return 기본 MountainForecastApiResponse 객체
     */
    public static MountainForecastApiResponse createDefault() {
        return new MountainForecastApiResponse(
                "20250814",         // baseDate
                "1300",             // baseTime
                "TMP",              // category (기온)
                "20250814",         // forecastDate
                "1400",             // forecastTime
                "25.5",             // forecastValue
                60,                 // nx
                127,                // ny
                "37.5665",          // lat
                "126.9780",         // lon
                "500",              // alt
                "서울"               // stationName
        );
    }

    /**
     * 특정 카테고리와 값을 가지는 커스텀 예보 응답 객체를 생성합니다.
     * @param category 예보 카테고리 (예: "POP", "SKY")
     * @param value 예보 값
     * @return 커스텀 MountainForecastApiResponse 객체
     */
    public static MountainForecastApiResponse create(String category, String value) {
        return new MountainForecastApiResponse(
                "20250814",
                "1300",
                category,           // 파라미터로 받은 카테고리
                "20250814",
                "1400",
                value,              // 파라미터로 받은 값
                60,
                127,
                "37.5665",
                "126.9780",
                "500",
                "서울"
        );
    }
}
