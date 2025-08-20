package com.softeer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;

    @Value("${cloud.aws.s3.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
//        return S3Client.builder()
//                .region(Region.AP_NORTHEAST_2)
//                .credentialsProvider(ProfileCredentialsProvider.create())
//                .build();
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint)) // LocalStack edge 포트
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test"))) // 더미
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // http://endpoint/bucket/key
                        .build())
                .build();
    }
}
