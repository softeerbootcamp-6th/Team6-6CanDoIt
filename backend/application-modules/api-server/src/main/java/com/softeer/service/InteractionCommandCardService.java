package com.softeer.service;

import com.softeer.domain.ImageMeta;
import com.softeer.dto.request.ReportCreateRequest;
import com.softeer.time.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class InteractionCommandCardService {

    private final ReportCommandUseCase reportCommandUseCase;
    private final ImageUseCase imageUseCase;
    private final CardHistoryUseCase cardHistoryUseCase;

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

        ReportCommandUseCase.ReportCreateDto reportCreateDto = new ReportCommandUseCase.ReportCreateDto(
                userId,
                request.courseId(),
                request.type(),
                request.content(),
                request.weatherKeywords(),
                request.rainKeywords(),
                request.etceteraKeywords(),
                imageId
        );

        reportCommandUseCase.saveReport(reportCreateDto);
    }

    public void likeReport(Long reportId, Long userId) {
        reportCommandUseCase.likeReport(reportId, userId);
    }


    public void upsertCardHistory(long userid, long courseId, LocalDateTime startDateTime) {
        LocalDateTime forecastDate = TimeUtil.getBaseTime(startDateTime);
        cardHistoryUseCase.upsertCardHistory(userid, courseId, forecastDate);
    }
}
