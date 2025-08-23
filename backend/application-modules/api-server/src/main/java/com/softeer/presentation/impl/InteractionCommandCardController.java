package com.softeer.presentation.impl;

import com.softeer.dto.request.ReportCreateRequest;
import com.softeer.presentation.InteractionCommandCardApi;
import com.softeer.service.InteractionCommandCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class InteractionCommandCardController implements InteractionCommandCardApi {

    private final InteractionCommandCardService interactionCommandCardService;

    @Override
    public ResponseEntity<HttpStatus> saveReport(
            ReportCreateRequest request,
            MultipartFile imageFile,
            Long userId
    ) {
        interactionCommandCardService.createReport(request, imageFile, userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<HttpStatus> likeReport(Long reportId, Long userId) {
        interactionCommandCardService.likeReport(reportId, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> upsertCardHistory(Long userId, long courseId, LocalDateTime startDateTime) {
        interactionCommandCardService.upsertCardHistory(userId, courseId, startDateTime);
        return ResponseEntity.ok().build();
    }
}
