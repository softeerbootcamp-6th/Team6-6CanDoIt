package com.softeer.shortterm.dto.request;

import com.softeer.common.ApiRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public record ShortForecastApiRequest(
        String serviceKey,
        int pageNo,
        int numOfRows,
        String dataType,
        String baseDate,
        String baseTime,
        int nx,
        int ny
) implements ApiRequest {

    @Override
    public String queryString() {
        String serviceKey = URLEncoder.encode(this.serviceKey, StandardCharsets.UTF_8);

        StringJoiner queryString = new StringJoiner("&");

        queryString.add("serviceKey=" + serviceKey);
        queryString.add("pageNo=" + this.pageNo);
        queryString.add("numOfRows=" + this.numOfRows);
        queryString.add("dataType=" + this.dataType);
        queryString.add("base_date=" + this.baseDate);
        queryString.add("base_time=" + this.baseTime);
        queryString.add("nx=" + this.nx);
        queryString.add("ny=" + this.ny);

        return queryString.toString();
    }
}
