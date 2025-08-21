package com.softeer.batch.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public static final String KMA_BASE_URL = "https://apihub.kma.go.kr";

    @Bean
    @Qualifier("mountainForecastApiWebClient")
    public WebClient mountainForecastApiRestClient() {
        return WebClient.builder()
                .baseUrl(KMA_BASE_URL)
                .build();
    }

    @Bean
    @Qualifier("kmaPublicApiWebClient")
    public WebClient kmaPublicApiRestClient() {
        return WebClient.builder()
                .baseUrl(KMA_BASE_URL)
                .build();
    }
}
