package com.softeer.shortterm;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class ShortForecastApiCaller extends AbstractShortForecastApiCaller {

    public ShortForecastApiCaller(WebClient webClient) {
        super(webClient);
    }

    @Override
    protected String getApiPath() {
        return "/api/typ02/openApi/VilageFcstInfoService_2.0/getVilageFcst";
    }
}
