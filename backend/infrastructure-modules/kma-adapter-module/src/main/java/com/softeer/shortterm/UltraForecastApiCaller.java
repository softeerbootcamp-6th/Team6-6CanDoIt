package com.softeer.shortterm;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.client.RestClient;

public class UltraForecastApiCaller extends AbstractShortForecastApiCaller{

    public UltraForecastApiCaller(RestClient restClient, XmlMapper xmlMapper) {
        super(restClient, xmlMapper);
    }

    @Override
    protected String getApiPath() {
        return "/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
    }
}
