package com.softeer.shortterm;

import org.springframework.web.reactive.function.client.WebClient;

public class UltraForecastApiCaller extends AbstractShortForecastApiCaller{

    public UltraForecastApiCaller(WebClient webClient) {
        super(webClient);
    }

    @Override
    protected String getApiPath() {
        return "/api/typ02/openApi/VilageFcstInfoService_2.0/getUltraSrtFcst";
    }
}
