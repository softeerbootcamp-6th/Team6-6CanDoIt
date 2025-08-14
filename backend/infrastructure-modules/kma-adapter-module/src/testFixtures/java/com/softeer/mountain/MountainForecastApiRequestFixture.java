package com.softeer.mountain;

import com.softeer.mountain.dto.MountainForecastApiRequest;

public final class MountainForecastApiRequestFixture {

    private MountainForecastApiRequestFixture() {}

    public static MountainForecastApiRequestBuilder builder() {
        return new MountainForecastApiRequestBuilder();
    }

    public static MountainForecastApiRequest createDefault() {
        return builder().build();
    }

    public static class MountainForecastApiRequestBuilder {
        private String authKey = "TEST_AUTH_KEY";
        private int mountainNum = 1;
        private String baseDate = "20231001";
        private String baseTime = "0600";

        public MountainForecastApiRequestBuilder authKey(String authKey) {
            this.authKey = authKey;
            return this;
        }

        public MountainForecastApiRequestBuilder mountainNum(int mountainNum) {
            this.mountainNum = mountainNum;
            return this;
        }

        public MountainForecastApiRequestBuilder baseDate(String baseDate) {
            this.baseDate = baseDate;
            return this;
        }

        public MountainForecastApiRequestBuilder baseTime(String baseTime) {
            this.baseTime = baseTime;
            return this;
        }

        public MountainForecastApiRequest build() {
            return new MountainForecastApiRequest(authKey, mountainNum, baseDate, baseTime);
        }
    }
}
