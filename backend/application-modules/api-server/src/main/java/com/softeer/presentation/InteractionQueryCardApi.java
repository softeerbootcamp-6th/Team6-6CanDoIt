package com.softeer.presentation;

import com.softeer.config.LoginUserId;
import com.softeer.domain.CardHistory;
import com.softeer.dto.response.card.ReportCardResponse;
import com.softeer.entity.enums.ReportType;
import com.softeer.service.ReportQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "InteractionQueryCardApi", description = "제보와 카드 기록 조회 API")
@RequestMapping("/card/interaction")
public interface InteractionQueryCardApi {

    @Operation(
            summary = "리포트 키워드 목록 조회",
            description = """
    ### 🧩 **리포트 키워드 그룹 응답 정보**

    해당 API는 리포트 작성 시 사용할 수 있는 **키워드 그룹 목록**을 반환합니다.  
    각 키워드는 `id`와 `description`을 포함하며, 카테고리별로 분류되어 제공됩니다.

    ---

    #### 📌 **응답 구조 (KeywordGroup)**
    - **weatherKeywords**: 날씨 관련 키워드 목록  
    - **rainKeywords**: 강수 관련 키워드 목록  
    - **etceteraKeywords**: 기타 키워드 목록  

    각 키워드 항목(Keyword)은 다음 필드로 구성됩니다:
    - **id**: 키워드 정수 ID  
    - **description**: 사용자에게 보여질 설명 문구

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    {
      "weatherKeywords": [
        { "id": 0, "description": "화창해요" },
        { "id": 1, "description": "구름이 많아요" },
        { "id": 2, "description": "더워요" },
        { "id": 3, "description": "추워요" }
      ],
      "rainKeywords": [
        { "id": 0, "description": "부슬비가 내려요" },
        { "id": 1, "description": "장대비가 쏟아져요" },
        { "id": 2, "description": "천둥 번개가 쳐요" },
        { "id": 3, "description": "폭우가 내려요" }
      ],
      "etceteraKeywords": [
        { "id": 0, "description": "안개가 껴요" },
        { "id": 1, "description": "미세먼지가 많아요" },
        { "id": 2, "description": "시야가 흐려요" }
      ]
    }
    ```
    """
    )
    @GetMapping("/keyword")
    ResponseEntity<ReportQueryUseCase.KeywordGroup> Keywords();

    @Operation(
            summary = "코스별 유저 리포트 목록 조회",
            description = """
    ### 📝 **코스별 유저 리포트 카드 응답 정보**

    해당 API는 특정 코스에 작성된 유저 리포트들을 **카드 형태로 리스트 반환**합니다.  
    요청 시, 리포트 타입 및 키워드 필터링, 페이징 쿼리를 적용할 수 있습니다.

    ---

    #### 🔗 **Path Parameter**
    - **courseId** (필수): 리포트를 조회할 코스 ID

    #### 🔍 **Query Parameters**
    - **reportType** (필수): 리포트 타입 (예: `WEATHER`, `SAFE`, `TRAIL`, `ETC`)  
      → 값이 없을 경우 `RPT-001`: `"제보 타입을 선택해주세요."` 에러 발생  
    - **pageSize** (선택): 한 번에 조회할 리포트 개수  
    - **lastId** (선택): 마지막으로 조회된 리포트 ID (커서 기반 페이징)  
    - **weatherKeywordIds** (선택): 날씨 키워드 필터링용 ID 목록  
    - **rainKeywordIds** (선택): 비/강수 키워드 필터링용 ID 목록  
    - **etceteraKeywordIds** (선택): 기타 키워드 필터링용 ID 목록

    ---

    #### 📌 **응답 필드 설명 (ReportCardResponse)**
    - **reportId**: 리포트 ID  
    - **reportType**: 리포트 종류 (예: WEATHER, SAFE 등)  
    - **createdAt**: 작성 시각 (ISO-8601)  
    - **nickname**: 작성자 닉네임  
    - **userImageUrl**: 작성자 프로필 이미지  
    - **imageUrl**: 리포트 이미지  
    - **content**: 리포트 본문  
    - **likeCount**: 좋아요 수  
    - **weatherKeywords**: 날씨 키워드 설명 문자열 리스트  
    - **rainKeywords**: 강수 키워드 설명 문자열 리스트  
    - **etceteraKeywords**: 기타 키워드 설명 문자열 리스트

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    [
      {
        "reportId": 101,
        "reportType": "WEATHER",
        "createdAt": "2025-08-18T09:12:00",
        "nickname": "등산고수",
        "userImageUrl": "https://cdn.example.com/users/u123.png",
        "imageUrl": "https://cdn.example.com/reports/r101.jpg",
        "content": "산 정상은 바람이 강해요. 주의하세요!",
        "likeCount": 24,
        "weatherKeywords": ["화창해요", "더워요"],
        "rainKeywords": ["부슬비가 내려요"],
        "etceteraKeywords": ["안개가 껴요"]
      }
    ]
    ```

    ---

    #### ❌ **오류 응답 예시 (HTTP 400 - reportType 누락)**
    ```json
    {
      "status": 400,
      "code": "RPT-001",
      "message": "제보 타입을 선택해주세요."
    }
    ```
    """
    )
    @GetMapping("/report/{courseId}")
    ResponseEntity<List<ReportCardResponse>> reports(@RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) Long lastId,
                                         @RequestParam(required = false) List<Integer> weatherKeywordIds,
                                         @RequestParam(required = false) List<Integer> rainKeywordIds,
                                         @RequestParam(required = false) List<Integer> etceteraKeywordIds,
                                         @PathVariable long courseId,
                                         @RequestParam ReportType reportType
    );


    @Operation(
            summary = "내가 작성한 리포트 목록 조회",
            description = """
    ### 🙋‍♂️ **내 리포트 카드 목록 응답 정보**

    해당 API는 인증된 사용자가 특정 코스에 대해 **작성한 리포트 목록**을 카드 형태로 반환합니다.  
    사용자 인증은 **Authorization 헤더에 Bearer JWT 토큰**을 포함해야 합니다.

    ---

    #### 🔐 **Authorization Header**
    - **Authorization** (필수): `Bearer {JWT_TOKEN}`  
      예: `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...`  
      → 누락되면 아래와 같은 예외 발생:  
      ```json
      {
        "status": 401,
        "code": "JWT-001",
        "message": "로그인이 필요한 서비스입니다."
      }
      ```

    ---

    #### 🔍 **Query Parameters**
    - **pageSize** (선택): 한 번에 조회할 리포트 개수  
    - **lastId** (선택): 마지막으로 조회한 리포트 ID (커서 기반 페이징)

    ---

    #### 📌 **응답 필드 설명 (ReportCardResponse)**
    - **reportId**: 리포트 ID  
    - **reportType**: 리포트 종류 (예: WEATHER, SAFE 등)  
    - **createdAt**: 작성 시각 (ISO-8601)  
    - **nickname**: 작성자 닉네임  
    - **userImageUrl**: 작성자 프로필 이미지  
    - **imageUrl**: 리포트 이미지  
    - **content**: 리포트 본문  
    - **likeCount**: 좋아요 수  
    - **weatherKeywords**: 날씨 키워드 설명 리스트  
    - **rainKeywords**: 강수 키워드 설명 리스트  
    - **etceteraKeywords**: 기타 키워드 설명 리스트

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    [
      {
        "reportId": 110,
        "reportType": "SAFE",
        "createdAt": "2025-08-18T09:40:00",
        "nickname": "나야나",
        "userImageUrl": "https://cdn.example.com/users/u77.jpg",
        "imageUrl": "https://cdn.example.com/reports/110.jpg",
        "content": "비탈길에 낙엽이 많아요. 미끄럼 주의!",
        "likeCount": 12,
        "weatherKeywords": ["구름이 많아요"],
        "rainKeywords": [],
        "etceteraKeywords": ["시야가 흐려요"]
      }
    ]
    ```
    """
    )
    @GetMapping("/report/me")
    ResponseEntity<List<ReportCardResponse>> myReports(@RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false) Long lastId,
                                                       @LoginUserId Long userId
    );

    @Operation(
            summary = "내가 좋아요 누른 리포트 목록 조회",
            description = """
    ### ❤️ **좋아요한 리포트 카드 목록 응답 정보**

    해당 API는 인증된 사용자가 특정 코스에 대해 **좋아요를 누른 리포트 목록**을 카드 형태로 반환합니다.  
    사용자 인증은 **Authorization 헤더에 Bearer JWT 토큰**을 포함해야 합니다.

    ---

    #### 🔐 **Authorization Header**
    - **Authorization** (필수): `Bearer {JWT_TOKEN}`  
      예: `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...`  
      → 누락 시 다음과 같은 예외 발생:  
      ```json
      {
        "status": 401,
        "code": "JWT-001",
        "message": "로그인이 필요한 서비스입니다."
      }
      ```

    ---

    #### 🔗 **Path Parameter**
    - **courseId** (필수): 좋아요한 리포트를 조회할 코스 ID

    #### 🔍 **Query Parameters**
    - **pageSize** (선택): 한 번에 조회할 리포트 개수  
    - **lastId** (선택): 마지막으로 조회한 리포트 ID (커서 기반 페이징)

    ---

    #### 📌 **응답 필드 설명 (ReportCardResponse)**
    - **reportId**: 리포트 ID  
    - **reportType**: 리포트 종류 (`WEATHER`, `SAFE`, `TRAIL`, `ETC` 등)  
    - **createdAt**: 작성 시각  
    - **nickname**: 작성자 닉네임  
    - **userImageUrl**: 작성자 프로필 이미지  
    - **imageUrl**: 리포트 이미지  
    - **content**: 리포트 본문  
    - **likeCount**: 좋아요 수  
    - **weatherKeywords**: 날씨 키워드 설명 리스트  
    - **rainKeywords**: 강수 키워드 설명 리스트  
    - **etceteraKeywords**: 기타 키워드 설명 리스트

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    [
      {
        "reportId": 125,
        "reportType": "TRAIL",
        "createdAt": "2025-08-17T14:10:00",
        "nickname": "산책매니아",
        "userImageUrl": "https://cdn.example.com/users/22.jpg",
        "imageUrl": "https://cdn.example.com/reports/125.jpg",
        "content": "등산로에 나뭇가지가 많이 떨어져 있어요.",
        "likeCount": 17,
        "weatherKeywords": ["구름이 많아요"],
        "rainKeywords": [],
        "etceteraKeywords": ["미세먼지가 많아요"]
      }
    ]
    ```
    """
    )
    @GetMapping("/report/me/like")
    ResponseEntity<List<ReportCardResponse>> likedReports(@RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) Long lastId,
                                              @LoginUserId Long userId
    );

    @Operation(
            summary = "내 카드 조회 이력 목록 조회",
            description = """
    ### 🗂️ **내 카드 조회 이력 응답 정보**

    해당 API는 로그인한 사용자가 이전에 조회한 **날씨 카드의 이력 목록**을 반환합니다.  
    응답은 가장 최근 조회 순으로 정렬되며, 커서 기반 페이징을 지원합니다.  
    사용자 인증은 **Authorization 헤더에 Bearer JWT 토큰**을 포함해야 합니다.

    ---

    #### 🔐 **Authorization Header**
    - **Authorization** (필수): `Bearer {JWT_TOKEN}`  
      예: `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...`  
      → 누락 시 다음과 같은 예외 발생:  
      ```json
      {
        "status": 401,
        "code": "JWT-001",
        "message": "로그인이 필요한 서비스입니다."
      }
      ```

    ---

    #### 🔍 **Query Parameters**
    - **pageSize** (선택): 한 번에 조회할 카드 이력 개수  
    - **lastId** (선택): 마지막으로 조회한 카드 이력 ID (커서 기반 페이징)

    ---

    #### 📌 **응답 필드 설명 (CardHistory)**
    - **id**: 이력 고유 ID  
    - **courseId**: 해당 코스 ID  
    - **mountainName**: 산 이름  
    - **courseName**: 코스 이름  
    - **forecastDate**: 날씨 예보 기준 시각  
    - **updatedAt**: 사용자가 마지막으로 카드를 조회한 시각

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    [
      {
        "id": 501,
        "courseId": 17,
        "mountainName": "지리산",
        "courseName": "세석코스",
        "forecastDate": "2025-08-18T06:00:00",
        "updatedAt": "2025-08-18T09:12:00"
      },
      {
        "id": 498,
        "courseId": 3,
        "mountainName": "설악산",
        "courseName": "공룡능선",
        "forecastDate": "2025-08-17T06:00:00",
        "updatedAt": "2025-08-17T18:40:00"
      }
    ]
    ```
    """
    )
    @GetMapping("/history")
    ResponseEntity<List<CardHistory>> myCardHistory(@RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) Long lastId,
                                                    @LoginUserId Long userId
    );
}
