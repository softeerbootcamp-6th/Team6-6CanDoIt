package com.softeer.batch.forecast.shortterm.config;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.forecast.shortterm.processor.ShortForecastProcessor;
import com.softeer.batch.forecast.shortterm.reader.ShortForecastReader;
import com.softeer.batch.forecast.shortterm.writer.ScheduledShortForecastWriter;
import com.softeer.batch.forecast.shortterm.writer.StartUpShortForecastWriter;
import com.softeer.domain.Grid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
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
public class ShortForecastJobConfig {

    public static final String START_UP_SHORT_FORECAST_JOB = "StartUpShortForecastJob";
    public static final String START_UP_SHORT_FORECAST_STEP = "StartUpShortForecastStep";
    public static final String SCHEDULED_SHORT_FORECAST_JOB = "ScheduledShortForecastJob";
    public static final String SCHEDULED_SHORT_FORECAST_STEP = "ScheduledShortForecastStep";

    private static final int CHUNK_SIZE = 20;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ShortForecastReader shortForecastReader;
    private final ShortForecastProcessor shortForecastProcessor;
    private final StartUpShortForecastWriter startShortForecastWriter;
    private final ScheduledShortForecastWriter scheduledShortForecastWriter;

    @Bean(name = START_UP_SHORT_FORECAST_JOB)
    public Job startupForecastJob() {
        return new JobBuilder(START_UP_SHORT_FORECAST_STEP, jobRepository)
                .start(startupShortForecastStep())
                .build();
    }

    @Bean(name = START_UP_SHORT_FORECAST_STEP)
    @JobScope
    public Step startupShortForecastStep() {
        return createForecastStep(START_UP_SHORT_FORECAST_STEP, startShortForecastWriter);
    }

    @Bean(name = SCHEDULED_SHORT_FORECAST_JOB)
    public Job scheduledForecastJob() {
        return new JobBuilder(SCHEDULED_SHORT_FORECAST_JOB, jobRepository)
                .start(scheduledShortForecastStep())
                .build();
    }

    @Bean(name = SCHEDULED_SHORT_FORECAST_STEP)
    @JobScope
    public Step scheduledShortForecastStep() {
        return createForecastStep(SCHEDULED_SHORT_FORECAST_STEP, scheduledShortForecastWriter);
    }

    private Step createForecastStep(String stepName, ItemWriter<ShortForecastList> writer) {
        return new StepBuilder(stepName, jobRepository)
                .<Grid, ShortForecastList>chunk(CHUNK_SIZE, transactionManager)
                .reader(shortForecastReader)
                .processor(shortForecastProcessor)
                .writer(writer)
//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
