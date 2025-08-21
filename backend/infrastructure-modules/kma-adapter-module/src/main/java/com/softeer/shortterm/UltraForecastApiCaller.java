package com.softeer.shortterm;

import org.springframework.web.client.RestClient;

public class UltraForecastApiCaller extends AbstractShortForecastApiCaller{

    public UltraForecastApiCaller(RestClient restClient) {
        super(restClient);
    }

    @Override
    protected String getApiPath() {
        return "/api/typ02/openApi/VilageFcstInfoService_2.0/getUltraSrtFcst";
    }
}
