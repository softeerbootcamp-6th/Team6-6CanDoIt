package com.softeer.batch.common.config;

import com.softeer.mountain.MountainForecastApiCaller;
import com.softeer.shortterm.ShortForecastApiCaller;
import com.softeer.shortterm.UltraForecastApiCaller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ForecastApiCallerConfig {

    @Bean
    public MountainForecastApiCaller mountainForecastApiCaller(
            @Qualifier("mountainForecastApiRestClient") RestClient restClient
    ) {
        return new MountainForecastApiCaller(restClient);
    }

    @Bean
    public ShortForecastApiCaller shortForecastApiCaller(
            @Qualifier("kmaPublicApiRestClient") RestClient restClient
    ) {
        return new ShortForecastApiCaller(restClient);
    }

    @Bean
    public UltraForecastApiCaller ultraForecastApiCaller(@Qualifier("kmaPublicApiRestClient") RestClient restClient
    ) {
        return new UltraForecastApiCaller(restClient);
    }
}
