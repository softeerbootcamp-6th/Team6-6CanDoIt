package com.softeer.batch.forecast.mountain.config;

import com.softeer.batch.forecast.mountain.dto.MountainDailyForecast;
import com.softeer.batch.forecast.mountain.dto.MountainIdentifier;
import com.softeer.batch.forecast.mountain.processor.MountainForecastProcessor;
import com.softeer.batch.forecast.mountain.reader.MountainIdentifierReader;
import com.softeer.batch.forecast.mountain.writer.ScheduledMountainForecastWriter;
import com.softeer.batch.forecast.mountain.writer.StartUpMountainForecastWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.softeer.batch.forecast.support.BatchNames.Steps.SCHEDULED_MOUNTAIN_FORECAST_STEP;
import static com.softeer.batch.forecast.support.BatchNames.Steps.STARTUP_MOUNTAIN_FORECAST_STEP;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MountainForecastJobConfig {

    private static final int CHUNK_SIZE = 20;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MountainIdentifierReader mountainIdentifierReader;
    private final MountainForecastProcessor mountainForecastProcessor;

    private final ScheduledMountainForecastWriter scheduledWriter;
    private final StartUpMountainForecastWriter startupWriter;

    @Bean(name = STARTUP_MOUNTAIN_FORECAST_STEP)
    @JobScope
    public Step startupForecastStep() {
        return createForecastStep(STARTUP_MOUNTAIN_FORECAST_STEP, startupWriter);
    }

    @Bean(name = SCHEDULED_MOUNTAIN_FORECAST_STEP)
    @JobScope
    public Step scheduledForecastStep() {
        return createForecastStep(SCHEDULED_MOUNTAIN_FORECAST_STEP, scheduledWriter);
    }

    private Step createForecastStep(String stepName, ItemWriter<MountainDailyForecast> writer) {
        return new StepBuilder(stepName, jobRepository)
                .<MountainIdentifier, MountainDailyForecast>chunk(CHUNK_SIZE, transactionManager)
                .reader(mountainIdentifierReader)
                .processor(mountainForecastProcessor)
                .writer(writer)
                .build();
    }
}
