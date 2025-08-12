package com.softeer.batch.mountain.service;

import com.softeer.batch.mountain.dto.MountainForecastApiResponse;
import com.softeer.domain.Mountain;
import com.softeer.time.ApiTime;
import com.softeer.time.ApiTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MountainForecastApiService {

    private final WebClient webClient;

    private static final String API_KEY = "";

    public MountainForecastApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apihub.kma.go.kr").build();
    }

    public List<MountainForecastApiResponse> fetchForecast(Mountain mountain, LocalDateTime dateTime) {
        ApiTime apiTime = ApiTimeUtil.getShortBaseTime(dateTime);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/typ08/getMountainWeather")
                        .queryParam("mountainNum", mountain.code())
                        .queryParam("base_date", apiTime.baseDate())
                        .queryParam("base_time", apiTime.baseTime())
                        .queryParam("authKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToFlux(MountainForecastApiResponse.class) // bodyToMono 대신 bodyToFlux 사용
                .collectList()
                .block();
    }
}
