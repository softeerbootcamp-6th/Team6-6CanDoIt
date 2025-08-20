package com.softeer.presentation;

import com.softeer.config.LoginUserId;
import com.softeer.dto.request.ReportCreateRequest;
import com.softeer.presentation.impl.InteractionCommandCardController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "InteractionQueryCardApi", description = "제보와 카드 기록 조회 API")
@RequestMapping("/card/interaction")
public interface InteractionCommandCardApi {

    @PostMapping("/report")
    ResponseEntity<HttpStatus> saveReport(
            @RequestPart("request") ReportCreateRequest request,
            @RequestPart("imageFile") MultipartFile imageFile,
            @LoginUserId Long userId
    );
}
