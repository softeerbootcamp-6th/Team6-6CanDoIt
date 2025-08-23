package com.softeer.presentation;

import com.softeer.config.LoginUserId;
import com.softeer.dto.request.ReportCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
                        "type": "WEATHER",  
                        "content": "정상에 안개가 자욱해요. 시야 확보가 어렵습니다.",
                        "weatherKeywords": [0, 1],
                        "rainKeywords": [],
                        "etceteraKeywords": [0, 2]
                      }
                      ```
                      type 필드의 경우 ["WEATHER", "SAFE"] 중 1개 선택.
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


    @Operation(
            summary = "사용자 카드 이력 수정 또는 추가",
            description = """
    ### 📝 **카드 이력 수정 또는 추가 (Upsert)**

    해당 API는 인증된 사용자가 특정 코스에 대한 **카드 이력을 수정하거나, 존재하지 않으면 새로 추가**하는 기능을 제공합니다.
    이력 수정 시, **startDateTime**을 요청 파라미터로 전달해야 하며, 코스 ID가 없다면 새로 추가됩니다.

    ---

    #### 🔐 **Authorization Header**
    - **Authorization** (필수): `Bearer {JWT_TOKEN}`
      예: `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...`
      → 누락 시 아래 예외 발생:
      ```json
      {
        "status": 401,
        "code": "JWT-001",
        "message": "로그인이 필요한 서비스입니다."
      }
      ```

    ---

    #### 🔗 **Path Parameter**
    - **courseId** (필수): 수정하거나 추가할 카드 이력의 코스 ID

    #### 🔍 **Query Parameters**
    - **startDateTime** (필수): 수정하거나 추가할 카드 이력의 시작 시간 (ISO-8601)

    ---

    #### ✅ **성공 응답 (HTTP 200)**
    - 내용 없음 (`200 OK`)
    """
    )
    @PutMapping("/history/{courseId}")
    ResponseEntity<Void> upsertCardHistory(@LoginUserId Long userId, @PathVariable long courseId, @RequestParam LocalDateTime startDateTime);
}
