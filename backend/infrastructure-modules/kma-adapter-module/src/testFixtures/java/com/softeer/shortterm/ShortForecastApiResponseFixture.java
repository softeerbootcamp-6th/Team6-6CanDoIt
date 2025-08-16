package com.softeer.shortterm;

import com.softeer.shortterm.dto.response.*;

import java.util.List;

public final class ShortForecastApiResponseFixture {

    private ShortForecastApiResponseFixture() {}

    public static ShortForecastApiResponseBuilder builder() {
        return new ShortForecastApiResponseBuilder();
    }

    public static ShortForecastApiResponse createDefault() {
        return builder().build();
    }

    public static ShortForecastApiResponse createWithItems(List<ShortForecastItem> shortForecastItems) {
        return builder().items(shortForecastItems).build();
    }

    public static class ShortForecastApiResponseBuilder {
        private ShortForecastHeader shortForecastHeader = new ShortForecastHeader("00", "NORMAL_SERVICE");
        private List<ShortForecastItem> shortForecastItems = List.of(ShortForecastItemFixture.createDefault());
        private int pageNo = 1;
        private int totalCount = 1;

        public ShortForecastApiResponseBuilder header(ShortForecastHeader shortForecastHeader) {
            this.shortForecastHeader = shortForecastHeader;
            return this;
        }

        public ShortForecastApiResponseBuilder items(List<ShortForecastItem> shortForecastItems) {
            this.shortForecastItems = shortForecastItems;
            this.totalCount = shortForecastItems.size();
            return this;
        }

        public ShortForecastApiResponse build() {
            ShortForecastItems responseShortForecastItems = new ShortForecastItems(shortForecastItems);
            ShortForecastBody shortForecastBody = new ShortForecastBody("JSON", responseShortForecastItems, pageNo, shortForecastItems.size(), totalCount);
            ShortForecastResponsePayload response = new ShortForecastResponsePayload(shortForecastHeader, shortForecastBody);
            return new ShortForecastApiResponse(response);
        }
    }
}
