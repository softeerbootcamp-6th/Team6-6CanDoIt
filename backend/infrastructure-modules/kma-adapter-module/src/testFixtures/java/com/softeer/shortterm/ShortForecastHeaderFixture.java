package com.softeer.shortterm;

import com.softeer.shortterm.dto.response.ShortForecastHeader;

public final class ShortForecastHeaderFixture {

    private ShortForecastHeaderFixture() {}

    public static ShortForecastHeaderBuilder builder() {
        return new ShortForecastHeaderBuilder();
    }

    public static ShortForecastHeader createDefault() {
        return builder().build();
    }

    public static class ShortForecastHeaderBuilder {
        private String resultCode = "00"; // 기본값: 정상 서비스
        private String resultMsg = "NORMAL_SERVICE"; // 기본값: 정상 서비스 메시지

        public ShortForecastHeaderBuilder resultCode(String resultCode) {
            this.resultCode = resultCode;
            return this;
        }

        public ShortForecastHeaderBuilder resultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
            return this;
        }

        public ShortForecastHeader build() {
            return new ShortForecastHeader(resultCode, resultMsg);
        }
    }
}
