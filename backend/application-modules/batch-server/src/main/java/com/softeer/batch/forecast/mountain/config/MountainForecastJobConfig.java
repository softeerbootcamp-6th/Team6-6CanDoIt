package com.softeer.batch.forecast.mountain.config;

import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.batch.forecast.mountain.dto.MountainIdentifier;
import com.softeer.batch.forecast.mountain.processor.MountainForecastProcessor;
import com.softeer.batch.forecast.mountain.reader.MountainIdentifierReader;
import com.softeer.batch.forecast.mountain.writer.ScheduledMountainForecastWriter;
import com.softeer.batch.forecast.mountain.writer.StartUpMountainForecastWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MountainForecastJobConfig {

    public static final String STARTUP_MOUNTAIN_FORECAST_JOB = "startupMountainForecastJob";
    public static final String STARTUP_MOUNTAIN_FORECAST_STEP = "startupMountainForecastStep";
    public static final String SCHEDULED_MOUNTAIN_FORECAST_JOB = "scheduledMountainForecastJob";
    public static final String SCHEDULED_MOUNTAIN_FORECAST_STEP = "scheduledMountainForecastStep";

    private static final int CHUNK_SIZE = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MountainIdentifierReader mountainIdentifierReader;
    private final MountainForecastProcessor mountainForecastProcessor;

    private final ScheduledMountainForecastWriter scheduledWriter;
    private final StartUpMountainForecastWriter startupWriter;

    @Bean(name = STARTUP_MOUNTAIN_FORECAST_JOB)
    public Job startupForecastJob() {
        return new JobBuilder(STARTUP_MOUNTAIN_FORECAST_JOB, jobRepository)
                .start(startupForecastStep())
                .build();
    }

    @Bean(name = STARTUP_MOUNTAIN_FORECAST_STEP)
    public Step startupForecastStep() {
        return createForecastStep(STARTUP_MOUNTAIN_FORECAST_STEP, startupWriter);
    }

    @Bean(name = SCHEDULED_MOUNTAIN_FORECAST_JOB)
    public Job scheduledForecastJob() {
        return new JobBuilder(SCHEDULED_MOUNTAIN_FORECAST_JOB, jobRepository)
                .start(scheduledForecastStep())
                .build();
    }

    @Bean(name = SCHEDULED_MOUNTAIN_FORECAST_STEP)
    public Step scheduledForecastStep() {
        return createForecastStep(SCHEDULED_MOUNTAIN_FORECAST_STEP, scheduledWriter);
    }

    private Step createForecastStep(String stepName, ItemWriter<MountainDailyForecast> writer) {
        return new StepBuilder(stepName, jobRepository)
                .<MountainIdentifier, MountainDailyForecast>chunk(CHUNK_SIZE, transactionManager)
                .reader(mountainIdentifierReader)
                .processor(mountainForecastProcessor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
