package com.softeer.mountain.dto;

import com.softeer.common.ApiRequest;

import java.util.StringJoiner;

public record MountainForecastApiRequest(
        String authKey,
        int mountainNum,
        String baseDate,
        String baseTime
) implements ApiRequest {

    @Override
    public String queryString() {
        StringJoiner joiner = new StringJoiner("&");

        joiner.add("authKey=" + authKey);
        joiner.add("mountainNum=" + mountainNum);
        joiner.add("base_date=" + baseDate);
        joiner.add("base_time=" + baseTime);

        return joiner.toString();
    }
}
