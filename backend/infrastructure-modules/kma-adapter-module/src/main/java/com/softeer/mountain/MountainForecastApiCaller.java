package com.softeer.mountain;

import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.config.ForecastApiType;
import com.softeer.mountain.dto.MountainForecastApiResponse;
import org.springframework.web.client.RestClient;

public class MountainForecastApiCaller extends AbstractKmaApiCaller<MountainForecastApiResponse> {

    public MountainForecastApiCaller(RestClient restClient) {
        super(restClient, ForecastApiType.MOUNTAIN);
    }

    @Override
    protected String getApiPath() {
        return "/api/typ08/getMountainWeather";
    }
}
