package com.softeer.mountain.dto;

import com.softeer.common.ApiRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.StringJoiner;

public record MountainForecastApiRequest(
        String authKey,
        int mountainNum,
        String baseDate,
        String baseTime
) implements ApiRequest {

    @Override
    public String queryString() {
        return UriComponentsBuilder.newInstance()
                .queryParam("authKey", this.authKey)
                .queryParam("mountainNum", this.mountainNum)
                .queryParam("base_date", this.baseDate)
                .queryParam("base_time", this.baseTime)
                .build()
                .getQuery();
    }
}
