package com.softeer.batch.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    public static final String KMA_BASE_URL = "https://apihub.kma.go.kr";

    @Bean
    @Qualifier("mountainForecastApiRestClient")
    public RestClient mountainForecastApiRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl(KMA_BASE_URL)
                .build();
    }

    @Bean
    @Qualifier("kmaPublicApiRestClient")
    public RestClient kmaPublicApiRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl(KMA_BASE_URL)
                .build();
    }
}
