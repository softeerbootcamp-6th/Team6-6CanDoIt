package com.softeer.batch.forecast.chained.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.softeer.batch.common.support.BatchNames.Jobs.SCHEDULED_CHAINED_FORECAST_JOB;
import static com.softeer.batch.common.support.BatchNames.Jobs.STARTUP_CHAINED_FORECAST_JOB;
import static com.softeer.batch.common.support.BatchNames.Steps.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChainedJobConfig {

    private final JobRepository jobRepository;

    @Bean(name = STARTUP_CHAINED_FORECAST_JOB)
    public Job startupJob(
            @Qualifier(START_UP_SHORT_FORECAST_STEP) Step startUpShortForecastStep,
            @Qualifier(STARTUP_MOUNTAIN_FORECAST_STEP) Step startupMountainForecastStep
    ) {
        return new JobBuilder(STARTUP_CHAINED_FORECAST_JOB, jobRepository)
                .start(startUpShortForecastStep)
                .next(startupMountainForecastStep)
                .build();
    }

    @Bean(name = SCHEDULED_CHAINED_FORECAST_JOB)
    public Job scheduledJob(
            @Qualifier(SCHEDULED_SHORT_FORECAST_STEP) Step scheduledShortForecastStep,
            @Qualifier(SCHEDULED_MOUNTAIN_FORECAST_STEP) Step scheduledMountainForecastStep
    ) {
        return new JobBuilder(SCHEDULED_CHAINED_FORECAST_JOB, jobRepository)
                .start(scheduledShortForecastStep)
                .next(scheduledMountainForecastStep)
                .build();
    }
}
