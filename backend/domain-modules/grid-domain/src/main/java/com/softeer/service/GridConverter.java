package com.softeer.service;

import com.softeer.domain.Coordinate;

public final class GridConverter {

    private GridConverter() {
    }

    private static final double PI = Math.asin(1.0) * 2.0;
    private static final double DEGRAD = PI / 180.0;

    private static final double RADIUS_OF_EARTH = 6371.00877; // 지구 평균 반경 (km)
    private static final double GRID_SIZE = 5.0; // 격자 간격 (km)
    private static final double STANDARD_LATITUDE_1 = 30.0; // 표준 위도 1 (degree)
    private static final double STANDARD_LATITUDE_2 = 60.0; // 표준
    private static final double STANDARD_LONGITUDE = 126.0; // 기준 경도 (degree)
    private static final double STANDARD_LATITUDE = 38.0; // 기준 위도 (격자)
    private static final double X_OFFSET = 210 / GRID_SIZE; // 기준점 X좌표 (격자)
    private static final double Y_OFFSET = 675 / GRID_SIZE; // 기준점 Y좌표 (격자)

    private static final double SN;
    private static final double SF;
    private static final double RO;

    static {
        double slat1Rad = STANDARD_LATITUDE_1 * DEGRAD;
        double slat2Rad = STANDARD_LATITUDE_2 * DEGRAD;

        double tempSn = Math.tan(PI * 0.25 + slat2Rad * 0.5) / Math.tan(PI * 0.25 + slat1Rad * 0.5);
        SN = Math.log(Math.cos(slat1Rad) / Math.cos(slat2Rad)) / Math.log(tempSn);

        double tempSf = Math.tan(PI * 0.25 + slat1Rad * 0.5);
        SF = Math.pow(tempSf, SN) * Math.cos(slat1Rad) / SN;

        double tempRo = Math.tan(PI * 0.25 + STANDARD_LATITUDE * DEGRAD * 0.5);
        RO = (RADIUS_OF_EARTH / GRID_SIZE) * SF / Math.pow(tempRo, SN);
    }

    /**
     * 위경도를 격자 좌표로 변환하는 공개 메소드
     *
     * @param longitude 경도 (degree)
     * @param latitude  위도 (degree)
     * @return 변환된 격자 좌표 Coordinate 객체
     */
    public static Coordinate convertWgsToGrid(double longitude, double latitude) {
        double ra = Math.tan(PI * 0.25 + latitude * DEGRAD * 0.5);
        ra = (RADIUS_OF_EARTH / GRID_SIZE) * SF / Math.pow(ra, SN);

        double theta = longitude * DEGRAD - (STANDARD_LONGITUDE * DEGRAD);
        if (theta > PI) theta -= 2.0 * PI;
        if (theta < -PI) theta += 2.0 * PI;
        theta *= SN;

        int gridX = (int) (ra * Math.sin(theta) + X_OFFSET + 1.5);
        int gridY = (int) (RO - ra * Math.cos(theta) + Y_OFFSET + 1.5);

        return new Coordinate(gridX, gridY);
    }
}
