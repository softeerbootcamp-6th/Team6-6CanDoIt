package com.softeer.batch.forecast.shortterm.config;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.forecast.shortterm.processor.ShortForecastProcessor;
import com.softeer.batch.forecast.shortterm.reader.ShortForecastReader;
import com.softeer.batch.forecast.shortterm.writer.ScheduledShortForecastWriter;
import com.softeer.batch.forecast.shortterm.writer.StartUpShortForecastWriter;
import com.softeer.domain.Grid;
import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.manager.retry.SimpleRetryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.softeer.batch.common.support.BatchNames.Handlers.SHORT_SIMPLE_RETRY_HANDLER;
import static com.softeer.batch.common.support.BatchNames.Steps.SCHEDULED_SHORT_FORECAST_STEP;
import static com.softeer.batch.common.support.BatchNames.Steps.START_UP_SHORT_FORECAST_STEP;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ShortForecastStepConfig {

    private static final int CHUNK_SIZE = 20;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ShortForecastReader shortForecastReader;
    private final ShortForecastProcessor shortForecastProcessor;
    private final StartUpShortForecastWriter startShortForecastWriter;
    private final ScheduledShortForecastWriter scheduledShortForecastWriter;

    @Bean(name = START_UP_SHORT_FORECAST_STEP)
    @JobScope
    public Step startupShortForecastStep() {
        return createForecastStep(START_UP_SHORT_FORECAST_STEP, startShortForecastWriter);
    }

    @Bean(name = SCHEDULED_SHORT_FORECAST_STEP)
    @JobScope
    public Step scheduledShortForecastStep() {
        return createForecastStep(SCHEDULED_SHORT_FORECAST_STEP, scheduledShortForecastWriter);
    }

    @Bean(name = SHORT_SIMPLE_RETRY_HANDLER)
    @StepScope
    public SimpleRetryHandler simpleRetryHandler() {
        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 500);
        return new SimpleRetryHandler(backoffStrategy, 5);
    }

    private Step createForecastStep(String stepName, ItemWriter<ShortForecastList> writer) {
        return new StepBuilder(stepName, jobRepository)
                .<Grid, ShortForecastList>chunk(CHUNK_SIZE, transactionManager)
                .reader(shortForecastReader)
                .processor(shortForecastProcessor)
                .writer(writer)
                .build();
    }
}
