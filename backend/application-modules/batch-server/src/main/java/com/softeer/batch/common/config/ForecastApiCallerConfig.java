package com.softeer.batch.common.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.softeer.mountain.MountainForecastApiCaller;
import com.softeer.shortterm.ShortForecastApiCaller;
import com.softeer.shortterm.UltraForecastApiCaller;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ForecastApiCallerConfig {

    @Bean
    @StepScope
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }

    @Bean
    @StepScope
    public MountainForecastApiCaller mountainForecastApiCaller(
            @Qualifier("mountainForecastApiRestClient") RestClient restClient,
            XmlMapper xmlMapper
    ) {
        return new MountainForecastApiCaller(restClient,  xmlMapper);
    }

    @Bean
    @StepScope
    public ShortForecastApiCaller shortForecastApiCaller(
            @Qualifier("kmaPublicApiRestClient") RestClient restClient,
            XmlMapper xmlMapper
    ) {
        return new ShortForecastApiCaller(restClient,  xmlMapper);
    }

    @Bean
    @StepScope
    public UltraForecastApiCaller ultraForecastApiCaller(@Qualifier("kmaPublicApiRestClient") RestClient restClient,
                                                         XmlMapper xmlMapper
    ) {
        return new UltraForecastApiCaller(restClient, xmlMapper);
    }
}
