package com.softeer.batch.forecast.ultra.v1;

import com.softeer.batch.forecast.shortterm.reader.ShortForecastReader;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.domain.Grid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UltraForecastV1JobConfig {

    public static final String SCHEDULED_ULTRA_FORECAST_V1_JOB = "ScheduledUltraForecastV1Job";
    public static final String SCHEDULED_ULTRA_FORECAST_V1_STEP = SCHEDULED_ULTRA_FORECAST_V1_JOB + "Step";

    private static final int CHUNK_SIZE = 30;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ShortForecastReader shortForecastReader;
    private final UltraForecastV1Processor ultraForecastV1Processor;
    private final UltraForecastV1Writer ultraForecastV1Writer;

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V1_JOB)
    public Job scheduledForecastJob() {
        return new JobBuilder(SCHEDULED_ULTRA_FORECAST_V1_JOB, jobRepository)
                .start(scheduledShortForecastStep())
                .build();
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V1_STEP)
    @StepScope
    public Step scheduledShortForecastStep() {
        return new StepBuilder(SCHEDULED_ULTRA_FORECAST_V1_STEP, jobRepository)
                .<Grid, UltraForecastResponseList>chunk(CHUNK_SIZE, transactionManager)
                .reader(shortForecastReader)
                .processor(ultraForecastV1Processor)
                .writer(ultraForecastV1Writer)
                .build();    }
}
