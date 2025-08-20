package com.softeer.presentation;

import com.softeer.config.LoginUserId;
import com.softeer.dto.request.ReportCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "InteractionCommandCardApi", description = "제보와 카드 생성 조회 API")
@RequestMapping("/card/interaction")
public interface InteractionCommandCardApi {

    @Operation(
            summary = "유저 리포트(제보) 작성",
            description = """
                    ### 📝 **유저 리포트(제보) 작성 정보**
                    
                    해당 API는 유저가 작성한 새로운 리포트(제보)를 **이미지 파일과 함께 `multipart/form-data` 형식으로 서버에 저장**합니다.  
                    사용자 인증은 **Authorization 헤더에 Bearer JWT 토큰**을 포함해야 합니다.
                    
                    ---
                    
                    #### 🔐 **Authorization Header**
                    - **Authorization** (필수): `Bearer {JWT_TOKEN}`  
                      → 누락 시 `JWT-001`: `"로그인이 필요한 서비스입니다."` 예외 발생
                    
                    ---
                    
                    #### 📦 **Request Parts (`multipart/form-data`)**
                    - **request** (`application/json`): 리포트의 메타데이터를 담는 JSON 객체입니다.
                      ```json
                      {
                        "courseId": 12,
                        "reportType": "WEATHER",  
                        "content": "정상에 안개가 자욱해요. 시야 확보가 어렵습니다.",
                        "weatherKeywordIds": [0, 1],
                        "rainKeywordIds": [],
                        "etceteraKeywordIds": [0, 2]
                      }
                      ```
                      reportType 필드의 경우 ["WEATHER", "SAFE"] 중 1개 선택.
                    - **imageFile** (`image/*`): 유저가 업로드하는 이미지 파일입니다. 필수로 전송해야 합니다.
                    
                    ---
                    
                    #### ✅ **성공 응답 (HTTP 201 Created)**
                    성공적으로 리포트가 생성되면, HTTP 상태 코드 201을 반환합니다. Body는 비어있습니다.
                    
                    ---
                    
                    #### ❌ **오류 응답 예시 (HTTP 401 - 인증 실패)**
                    ```json
                    {
                      "status": 401,
                      "code": "JWT-001",
                      "message": "로그인이 필요한 서비스입니다."
                    }
                    ```
                    """
    )
    @PostMapping("/report")
    ResponseEntity<HttpStatus> saveReport(
            @RequestPart("request") ReportCreateRequest request,
            @RequestPart("imageFile") MultipartFile imageFile,
            @LoginUserId Long userId
    );

    @Operation(
            summary = "리포트(제보) 좋아요",
            description = """
                      @Operation(
                          summary = "리포트 좋아요 토글",
                          description = ""\"
                      ### 💗 **리포트 좋아요 토글**
                    
                      요청한 `reportId`에 대해 **좋아요를 토글**합니다. \s
                      - 이미 좋아요가 **존재하면 삭제(좋아요 취소)** \s
                      - 좋아요가 **없으면 생성(좋아요 추가)**
                    
                      ---
                    
                      #### 🔐 **Authorization Header**
                      - **Authorization** (필수): `Bearer {JWT_TOKEN}` \s
                        → 누락 시 `JWT-001`: `"로그인이 필요한 서비스입니다."` 예외 발생
                    
                      ---
                    
                      #### 🔗 **Path Variable**
                      - **reportId** (필수): 좋아요를 토글할 리포트의 식별자
                    
                      ---
                    
                      #### ✅ **성공 응답 (HTTP 200 OK)**
                      - 본 메서드는 토글 결과에 상관없이 **200 OK**를 반환합니다. Body는 비어있습니다.
                        - 좋아요 추가됨 (기존에 없던 경우)
                        - 좋아요 취소됨 (기존에 있던 경우)
                    
                      ---
                    
                      #### ❌ **오류 응답 예시**
                      - **HTTP 401 (인증 실패)** \s
                        ```json
                        {
                          "status": 401,
                          "code": "JWT-001",
                          "message": "로그인이 필요한 서비스입니다."
                        }
                    """
    )
    @PostMapping("/report/like/{reportId}")
    ResponseEntity<HttpStatus> likeReport(
            @PathVariable(value = "reportId") Long reportId,
            @LoginUserId Long userId
    );
}
