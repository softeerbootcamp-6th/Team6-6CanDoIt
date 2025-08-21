package com.softeer.batch.forecast.ultra.v2;

import com.softeer.batch.forecast.shortterm.reader.ShortForecastReader;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.domain.Grid;
import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.manager.retry.AsyncRetryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UltraForecastV2JobConfig {

    public static final String SCHEDULED_ULTRA_FORECAST_V2_JOB = "ScheduledUltraForecastV2Job";
    public static final String SCHEDULED_ULTRA_FORECAST_V2_STEP = SCHEDULED_ULTRA_FORECAST_V2_JOB + "Step";
    public static final String SCHEDULED_ULTRA_FORECAST_V2_EX_SERVICE = SCHEDULED_ULTRA_FORECAST_V2_STEP + "ExecutorService";

    private static final int CHUNK_SIZE = 30;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ShortForecastReader shortForecastReader;
    private final UltraForecastV2Processor ultraForecastV2Processor;
    private final UltraForecastV2Writer ultraForecastV2Writer;

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V2_JOB)
    public Job scheduledForecastJob() {
        return new JobBuilder(SCHEDULED_ULTRA_FORECAST_V2_JOB, jobRepository)
                .start(scheduledShortForecastStep())
                .build();
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V2_STEP)
    @StepScope
    public Step scheduledShortForecastStep() {
        return new StepBuilder(SCHEDULED_ULTRA_FORECAST_V2_STEP, jobRepository)
                .<Grid, CompletableFuture<UltraForecastResponseList>>chunk(CHUNK_SIZE, transactionManager)
                .reader(shortForecastReader)
                .processor(ultraForecastV2Processor)
                .writer(ultraForecastV2Writer)
                .build();
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V2_EX_SERVICE)
    @StepScope
    public ExecutorService scheduledForecastThreadPoolTaskExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    @StepScope
    public AsyncRetryHandler simpleHandleRetryManager() {
        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 400);
        return new AsyncRetryHandler(backoffStrategy, 5);
    }
}
