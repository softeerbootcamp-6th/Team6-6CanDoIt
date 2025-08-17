package com.softeer.shortterm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

@Slf4j
public class ShortForecastApiCaller extends AbstractShortForecastApiCaller {

    public ShortForecastApiCaller(RestClient restClient) {
        super(restClient);
    }

    @Override
    protected String getApiPath() {
        return "/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    }
}
