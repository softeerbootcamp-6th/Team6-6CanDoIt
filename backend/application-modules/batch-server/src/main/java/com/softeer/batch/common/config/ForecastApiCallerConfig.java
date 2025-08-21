package com.softeer.batch.common.config;

import com.softeer.mountain.MountainForecastApiCaller;
import com.softeer.shortterm.ShortForecastApiCaller;
import com.softeer.shortterm.UltraForecastApiCaller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ForecastApiCallerConfig {

    @Bean
    public MountainForecastApiCaller mountainForecastApiCaller(
            @Qualifier("mountainForecastApiWebClient") WebClient webClient
    ) {
        return new MountainForecastApiCaller(webClient);
    }

    @Bean
    public ShortForecastApiCaller shortForecastApiCaller(
            @Qualifier("kmaPublicApiWebClient") WebClient webClient
    ) {
        return new ShortForecastApiCaller(webClient);
    }

    @Bean
    public UltraForecastApiCaller ultraForecastApiCaller(@Qualifier("kmaPublicApiRestClient") WebClient webClient
    ) {
        return new UltraForecastApiCaller(webClient);
    }
}
