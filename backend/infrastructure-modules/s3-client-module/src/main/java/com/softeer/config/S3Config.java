package com.softeer.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class S3Config {

    @Bean
    @Profile("!test & !local")
    public S3Client s3ClientProd() {
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    @Profile("test | local")
    public S3Client s3ClientLocal(S3Properties props) {
        S3ClientBuilder b = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create());

        if (props.endpoint() != null && !props.endpoint().isBlank()) {
            b = b.endpointOverride(URI.create(props.endpoint()))
                    .serviceConfiguration(
                            S3Configuration.builder()
                                    .pathStyleAccessEnabled(Boolean.TRUE.equals(props.pathStyle()))
                                    .build()
                    );
        }
        return b.build();
    }
}
