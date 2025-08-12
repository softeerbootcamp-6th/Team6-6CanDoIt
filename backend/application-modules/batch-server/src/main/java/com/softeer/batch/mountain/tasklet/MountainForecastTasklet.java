package com.softeer.batch.mountain.tasklet;

import com.softeer.batch.mountain.dto.MountainForecastApiResponse;
import com.softeer.batch.mountain.service.MountainForecastApiService;
import com.softeer.domain.Forecast;
import com.softeer.domain.Mountain;
import com.softeer.entity.enums.ForecastType;
import com.softeer.entity.enums.PrecipitationType;
import com.softeer.entity.enums.Sky;
import com.softeer.entity.enums.WindDirection;
import com.softeer.service.MountainUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MountainForecastTasklet implements Tasklet {
    private static final Logger log = LoggerFactory.getLogger(MountainForecastTasklet.class);

    private final MountainForecastApiService apiService;

    @Autowired
    private final MountainUseCase mountainUseCase;

    public MountainForecastTasklet(MountainForecastApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        // JobParameter에서 scheduledAt 꺼내오기(없으면 now)
        String scheduledAtStr = (String) Objects.requireNonNullElse(
                chunkContext.getStepContext().getJobParameters().get("scheduledAt"),
                LocalDateTime.now().toString()
        );
        LocalDateTime scheduledAt = LocalDateTime.parse(scheduledAtStr);

        log.info("MountainForecastTasklet started. scheduledAt={}", scheduledAt);

        List<Mountain> mountains = mountainUseCase.getMountains();
        for (Mountain mountain : mountains) {
            // 1. API 호출하여 DTO 리스트를 받습니다.
            List<MountainForecastApiResponse> items = apiService.fetchForecast(mountain, scheduledAt);

            // 2. DTO 리스트를 Forecast 도메인 객체로 변환합니다.
            List<Forecast> forecasts = convertToDomainList(items);

            // 3. TODO: 변환된 Forecast 객체를 DB에 저장하는 로직 호출
            // mountainUseCase.saveForecast(forecast);
            log.info("Converted forecast for {}: {}", mountain.name(), forecasts);
        }

        log.info("MountainForecastTasklet finished.");
        return RepeatStatus.FINISHED;
    }

    /**
     * API 응답 DTO 리스트를 시간대별 Forecast 도메인 객체 리스트로 변환합니다.
     * @param items API에서 받은 전체 예보 아이템 리스트
     * @return 시간대별로 정리된 Forecast 객체 리스트
     */
    private List<Forecast> convertToDomainList(List<MountainForecastApiResponse> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. DTO 리스트를 예보 시간(LocalDateTime)을 기준으로 그룹핑합니다.
        // 결과: Map<시간, 해당 시간의 예보 아이템 리스트>
        Map<LocalDateTime, List<MountainForecastApiResponse>> groupedByTime = items.stream()
                .collect(Collectors.groupingBy(item ->
                        LocalDateTime.of(
                                LocalDate.parse(item.forecastDate(), DateTimeFormatter.ofPattern("yyyyMMdd")),
                                LocalTime.parse(item.forecastTime(), DateTimeFormatter.ofPattern("HHmm"))
                        )
                ));

        // 2. 그룹핑된 Map을 순회하며 각 시간대별로 Forecast 객체를 생성합니다.
        return groupedByTime.entrySet().stream()
                .map(entry -> {
                    LocalDateTime dateTime = entry.getKey();
                    List<MountainForecastApiResponse> hourlyItems = entry.getValue();

                    // 3. 해당 시간대의 예보 아이템들을 category를 key로 하는 Map으로 변환합니다.
                    Map<String, String> forecastData = hourlyItems.stream()
                            .collect(Collectors.toMap(
                                    MountainForecastApiResponse::category,
                                    MountainForecastApiResponse::forecastValue,
                                    (value1, value2) -> value1 // 중복 키 무시
                            ));

                    // 4. 추출한 데이터로 Forecast 객체를 생성합니다.
                    return new Forecast(
                            0L,
                            dateTime,
                            ForecastType.MOUNTAIN,
                            Sky.fromCode(forecastData.get("SKY")),
                            Double.parseDouble(forecastData.getOrDefault("TMP", "0.0")), // 기온
                            Double.parseDouble(forecastData.getOrDefault("REH", "0.0")), // 습도
                            WindDirection.fromCode(forecastData.getOrDefault("VEC", "0")),
                            Double.parseDouble(forecastData.getOrDefault("WSD", "0.0")), // 풍속
                            PrecipitationType.fromCode(forecastData.getOrDefault("PTY", "0")), // 강수 형태
                            forecastData.getOrDefault("PCP", "강수없음"),
                            Double.parseDouble(forecastData.getOrDefault("POP", "0.0")),
                            forecastData.getOrDefault("SNO", "적설없음"),
                            // 일 최고/최저 기온(TMX/TMN)은 시간별 예보에 없을 수 있으므로 주의
                            Double.parseDouble(forecastData.getOrDefault("TMX", "0.0")),
                            Double.parseDouble(forecastData.getOrDefault("TMN", "0.0"))
                    );
                })
                .sorted(Comparator.comparing(Forecast::dateTime)) // 시간순으로 정렬
                .collect(Collectors.toList());
    }
}