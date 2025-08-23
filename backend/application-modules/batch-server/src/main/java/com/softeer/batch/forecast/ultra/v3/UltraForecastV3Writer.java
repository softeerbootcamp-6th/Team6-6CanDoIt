package com.softeer.batch.forecast.ultra.v3;

import com.softeer.batch.forecast.ultra.UltraForecastJdbcWriter;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.batch.forecast.ultra.redis.UltraForecastRedisWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@StepScope
@RequiredArgsConstructor
public class UltraForecastV3Writer implements ItemWriter<CompletableFuture<UltraForecastResponseList>> {

    private final UltraForecastJdbcWriter ultraForecastJdbcWriter;
    private final UltraForecastRedisWriter ultraForecastRedisWriter;

    @Override
    public void write(Chunk<? extends CompletableFuture<UltraForecastResponseList>> chunk) {
        List<UltraForecastResponseList> results = chunk.getItems().stream()
                .map(CompletableFuture::join) // 예외 발생 시 RuntimeException 으로 전파
                .toList();

        ultraForecastJdbcWriter.batchUpdateForecast(results);
        ultraForecastRedisWriter.pipelineUpdateUltraForecast(results);
    }
}
