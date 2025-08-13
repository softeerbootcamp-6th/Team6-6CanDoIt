package com.softeer.presentation;

import com.softeer.dto.response.HourlyWeatherResponse;
import com.softeer.dto.response.card.CourseCardResponse;
import com.softeer.dto.response.card.ForecastCardResponse;
import com.softeer.dto.response.card.MountainCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "WeatherCardApi", description = "카드와 관련된 API")
@RequestMapping("/card")
public interface WeatherCardApi {

    @Operation(
            summary = "산 카드 정보 목록 조회",
            description = """
    **🏔️ 산 카드 응답 정보**
    
    해당 API는 여러 산에 대한 기본 정보와 날씨 정보를 포함한 **카드 형태의 응답 리스트**를 제공합니다.
    
    ---
    
    **📌 Response 필드 설명**
    - **mountainName**: 산 이름  
    - **mountainImageUrl**: 산 대표 이미지 URL  
    - **mountainDescription**: 산 설명 문구  
    - **weatherMetric**: 날씨 정보 객체  
      - **precipitationType**: 강수 형태 (예: NONE, RAIN, SNOW 등)  
      - **sky**: 하늘 상태 (예: SUNNY, CLOUDY, OVERCAST 등)  
      - **surfaceTemperature**: 해발 하단부 기온 (°C)  
      - **topTemperature**: 정상 기온 (°C)
    
    ---
    
    #### ✅ 성공 응답 예시 (HTTP 200)**  
    ```json
    [  
      {  
        "mountainName": "태백산",  
        "mountainImageUrl": "https://cdn.example.com/images/taebaek.png",  
        "mountainDescription": "한겨울 설경이 아름다운 산입니다.",  
        "weatherMetric": {  
          "precipitationType": "NONE",  
          "sky": "SUNNY",  
          "surfaceTemperature": 23.5,  
          "topTemperature": 18.2  
        }  
      },  
      {  
        "mountainName": "지리산",  
        "mountainImageUrl": "https://cdn.example.com/images/jiri.png",  
        "mountainDescription": "한국에서 두 번째로 높은 산입니다.",  
        "weatherMetric": {  
          "precipitationType": "RAIN",  
          "sky": "OVERCAST",  
          "surfaceTemperature": 20.1,  
          "topTemperature": 15.3  
        }  
      }  
    ]
    ```
    """
    )
    @GetMapping("/mountains")
    ResponseEntity<List<MountainCardResponse>> mountainCards();


    @Operation(
            summary = "코스 카드 정보 조회",
            description = """
    ### 🥾 **코스 카드 응답 정보**

    해당 API는 특정 등산 코스에 대한 이미지, 거리·소요시간, 날씨 정보, 그리고 산악활동지수(하이킹 적합도)를 포함한 **카드 형태의 응답**을 제공합니다.

    ---

    #### 📌 **Response 필드 설명**
    - **courseImageUrl**: 코스 대표 이미지 URL
    - **totalDuration**: 총 예상 소요 시간 (단위: 시간)
    - **totalDistance**: 총 거리 (단위: km)
    - **weatherMetric**: 날씨 정보 객체
      - **precipitationType**: 강수 형태 (예: NONE, RAIN, SNOW 등)
      - **sky**: 하늘 상태 (예: SUNNY, CLOUDY, OVERCAST 등)  
      - **surfaceTemperature**: 시작점 기온 (°C)  
      - **topTemperature**: 정상 기온 (°C)  
    - **hikingActivityStatus**: 산악활동지수 (예: 매우좋음, 보통, 나쁨 등)  

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    {
      "courseImageUrl": "https://cdn.example.com/images/course01.png",
      "totalDuration": 2.5,
      "totalDistance": 4.8,
      "weatherMetric": {
        "precipitationType": "NONE",
        "sky": "CLOUDY",
        "surfaceTemperature": 21.3,
        "topTemperature": 17.0
      },
      "hikingActivityStatus": "좋음"
    }
    ```
    """
    )
    @GetMapping("/course/{courseId}")
    ResponseEntity<CourseCardResponse> courseCard(@PathVariable Long courseId, @RequestParam LocalDateTime dateTime);

    @Operation(
            summary = "코스 예보 카드 조회",
            description = """
    ### 🌦️ **코스 날씨 예보 카드 응답 정보**

    해당 API는 특정 등산 코스에 대해 **출발 시점부터 하산까지의 시간대별 날씨 정보**와 **고도에 따른 보정 정보**를 제공합니다.  
    각 시간대별 예보는 상세한 기온, 체감온도, 하늘 상태, 강수량, 바람, 습도 등으로 구성되며,  
    **추천 복장/행동 코멘트**도 함께 제공됩니다.

    ---

    #### 📌 **Response 필드 설명**

    - **startCard**: 출발 시점 날씨 정보  
    - **arrivalCard**: 정상 도착 시점 날씨 정보  
    - **adjustedArrivalCard**: 고도 보정된 정상 도착 시점 날씨 정보  
    - **descentCard**: 하산 시작 시점 날씨 정보  
    - **courseAltitude**: 코스의 최고 고도 (m)  
    - **recommendComment**: 예보 기반 추천 코멘트 (예: 바람막이가 필요할 거예요)  
    - **adjustedRecommendComment**: 고도 보정된 예보 기반 추천 코멘트  

    각 카드(ForecastCardDetail)는 다음과 같은 필드로 구성됩니다:

    - **dateTime**: 예보 시각  
    - **hikingActivity**: 산악활동지수 (예: 매우좋음, 보통, 나쁨 등)  
    - **temperature**: 기온 (°C)  
    - **apparentTemperature**: 체감온도 (°C)  
    - **temperatureDescription**: 기온 관련 설명  
    - **precipitation**: 예상 강수량 (mm)  
    - **probabilityDescription**: 강수 확률 설명  
    - **precipitationType**: 강수 형태 (예: NONE, RAIN, SNOW 등)  
    - **sky**: 하늘 상태 (예: SUNNY, CLOUDY, OVERCAST 등)  
    - **skyDescription**: 하늘 상태 설명  
    - **windSpeed**: 풍속 (m/s)  
    - **windSpeedDescription**: 풍속 설명  
    - **humidity**: 습도 (%)  
    - **humidityDescription**: 습도 설명  
    - **highestTemperature**: 해당 일자의 최고 기온 (°C)  
    - **lowestTemperature**: 해당 일자의 최저 기온 (°C)

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    {
      "startCard": {
        "dateTime": "2025-08-12T06:00:00",
        "hikingActivity": "좋음",
        "temperature": 20.1,
        "apparentTemperature": 19.5,
        "temperatureDescription": "쾌적한 기온입니다.",
        "precipitation": "0.0",
        "probabilityDescription": "비 올 가능성 낮음",
        "precipitationType": "NONE",
        "sky": "SUNNY",
        "skyDescription": "맑은 하늘",
        "windSpeed": 1.2,
        "windSpeedDescription": "약한 바람",
        "humidity": 55.0,
        "humidityDescription": "적정 습도",
        "highestTemperature": 26.0,
        "lowestTemperature": 17.0
      },
      "arrivalCard": { ... },
      "adjustedArrivalCard": { ... },
      "descentCard": { ... },
      "courseAltitude": 820,
      "recommendComment": "바람막이가 필요할 거예요",
      "adjustedRecommendComment": "정상은 더 추우니 따뜻하게 입으세요"
    }
    ```
    """
    )
    @GetMapping("/course/{courseId}/forecast")
    ResponseEntity<ForecastCardResponse> forecastCard(@PathVariable Long courseId, @RequestParam LocalDateTime startDateTime);

    @Operation(
            summary = "날씨 시간별 예보 조회",
            description = """
    ### 🕐 **시간대별 산 날씨 예보 응답 정보**

    해당 API는 특정 산에 대해 **startDateTime 시점부터의 시간대별 기온, 하늘 상태, 강수 형태**를 제공합니다.  
    예보 단위는 시간(Hour)이며, **시계열 그래프, 카드 UI 등에서 활용하기 적합한 구조**입니다.

    ---

    #### 📌 **Response 필드 설명**
    - **dateTime**: 예보 시각 (`yyyy-MM-ddTHH:mm:ss`)  
    - **temperature**: 예보 기온 (°C)  
    - **sky**: 하늘 상태 (예: SUNNY, CLOUDY, OVERCAST 등)  
    - **precipitationType**: 강수 형태 (예: NONE, RAIN, SNOW 등)

    ---

    #### ✅ **성공 응답 예시 (HTTP 200)**
    ```json
    [
      {
        "dateTime": "2025-08-12T09:00:00",
        "temperature": 22.3,
        "sky": "SUNNY",
        "precipitationType": "NONE"
      },
      {
        "dateTime": "2025-08-12T10:00:00",
        "temperature": 23.1,
        "sky": "CLOUDY",
        "precipitationType": "NONE"
      },
      {
        "dateTime": "2025-08-12T11:00:00",
        "temperature": 24.0,
        "sky": "OVERCAST",
        "precipitationType": "RAIN"
      }
    ]
    ```
    """
    )
    @GetMapping("/mountain/{mountainId}/forecast")
    ResponseEntity<List<HourlyWeatherResponse>> hourlyWeatherForecasts(@PathVariable Long mountainId, @RequestParam LocalDateTime startDateTime);


}
