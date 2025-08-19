package com.softeer.batch.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    public static final String MOUNTAIN_BASE_URL = "https://apihub.kma.go.kr";
    public static final String KMA_PUBLIC_BASE_URL = "https://apis.data.go.kr";

    @Bean
    @Qualifier("mountainForecastApiRestClient")
    public RestClient mountainForecastApiRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl(MOUNTAIN_BASE_URL)
                .build();
    }

    @Bean
    @Qualifier("kmaPublicApiRestClient")
    public RestClient kmaPublicApiRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl(KMA_PUBLIC_BASE_URL)
                .build();
    }
}
