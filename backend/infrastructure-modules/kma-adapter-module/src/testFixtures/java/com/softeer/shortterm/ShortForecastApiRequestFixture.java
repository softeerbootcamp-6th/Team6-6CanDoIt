package com.softeer.shortterm;

import com.softeer.shortterm.dto.request.ShortForecastApiRequest;

public final class ShortForecastApiRequestFixture {

    private ShortForecastApiRequestFixture() {}

    public static ShortForecastApiRequestBuilder builder() {
        return new ShortForecastApiRequestBuilder();
    }

    public static ShortForecastApiRequest createDefault() {
        return builder().build();
    }

    public static class ShortForecastApiRequestBuilder {
        private String serviceKey = "TEST_SERVICE_KEY";
        private int pageNo = 1;
        private int numOfRows = 100;
        private String dataType = "JSON";
        private String baseDate = "20250816";
        private String baseTime = "0500";
        private int nx = 60;
        private int ny = 127;

        public ShortForecastApiRequestBuilder serviceKey(String serviceKey) {
            this.serviceKey = serviceKey;
            return this;
        }

        public ShortForecastApiRequestBuilder pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public ShortForecastApiRequestBuilder numOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
            return this;
        }

        public ShortForecastApiRequestBuilder baseDate(String baseDate) {
            this.baseDate = baseDate;
            return this;
        }

        public ShortForecastApiRequestBuilder baseTime(String baseTime) {
            this.baseTime = baseTime;
            return this;
        }

        public ShortForecastApiRequestBuilder nx(int nx) {
            this.nx = nx;
            return this;
        }

        public ShortForecastApiRequestBuilder ny(int ny) {
            this.ny = ny;
            return this;
        }

        public ShortForecastApiRequest build() {
            return new ShortForecastApiRequest(
                    serviceKey,
                    pageNo,
                    numOfRows,
                    dataType,
                    baseDate,
                    baseTime,
                    nx,
                    ny
            );
        }
    }
}
