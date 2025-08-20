package com.softeer.service;

import com.softeer.domain.ImageMeta;
import com.softeer.dto.ReportCreateDto;
import com.softeer.dto.request.ReportCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class InteractionCommandCardService {

    private final ReportJpaUseCase reportJpaUseCase;
    private final ImageUseCase imageUseCase;

    public void createReport(
            ReportCreateRequest request,
            MultipartFile imageFile,
            long userId
    ) {
        long imageId;

        try {
            ImageMeta imageMeta = new ImageMeta(imageFile.getBytes(), imageFile.getName(), imageFile.getContentType());
            imageId = imageUseCase.uploadImage(imageMeta);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        ReportCreateDto reportCreateDto = new ReportCreateDto(
                userId,
                request.courseId(),
                request.type(),
                request.content(),
                request.weatherKeywords(),
                request.rainKeywords(),
                request.etceteraKeywords(),
                imageId
        );

        reportJpaUseCase.saveReport(reportCreateDto);
    }
}
