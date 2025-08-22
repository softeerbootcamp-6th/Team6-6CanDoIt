package com.softeer.service;

import com.softeer.config.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URL;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Properties props;

    public String uploadFile(byte[] data, String filename, String contentType) {
        String key = "report/" + UUID.randomUUID() + "_" + filename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(props.bucket())
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data));

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(props.bucket())
                .key(key)
                .build();

        URL url = s3Client.utilities().getUrl(getUrlRequest);

        return url.toString();
    }
}
