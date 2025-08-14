package com.softeer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @Qualifier("mountainForecastApiWebClient")
    public RestClient mountainForecastApiRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl("https://apihub.kma.go.kr")
                .build();
    }

    @Bean
    @Qualifier("kmaPublicApiWebClient")
    public RestClient kmaPublicApiRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl("https://apis.data.go.kr")
                .build();
    }
}