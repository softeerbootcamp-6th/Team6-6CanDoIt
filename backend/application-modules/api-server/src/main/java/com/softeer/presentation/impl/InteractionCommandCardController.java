package com.softeer.presentation.impl;

import com.softeer.dto.request.ReportCreateRequest;
import com.softeer.presentation.InteractionCommandCardApi;
import com.softeer.service.InteractionCommandCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

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
}
