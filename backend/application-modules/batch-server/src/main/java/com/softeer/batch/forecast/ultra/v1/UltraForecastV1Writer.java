package com.softeer.batch.forecast.ultra.v1;

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

@Component
@Slf4j
@StepScope
@RequiredArgsConstructor
public class UltraForecastV1Writer implements ItemWriter<UltraForecastResponseList> {

    private final UltraForecastJdbcWriter ultraForecastJdbcWriter;
    private final UltraForecastRedisWriter ultraForecastRedisWriter;

    @Override
    public void write(Chunk<? extends UltraForecastResponseList> chunk) {
        List<? extends UltraForecastResponseList> items = chunk.getItems();

        ultraForecastJdbcWriter.batchUpdateForecast(items);

        ultraForecastRedisWriter.pipelineUpdateUltraForecast(items);
    }
}
