package com.softeer.batch.forecast.shortterm.config;

import com.softeer.batch.forecast.shortterm.dto.ShortForecastList;
import com.softeer.batch.forecast.shortterm.listener.DailyTemperatureCollector;
import com.softeer.batch.forecast.shortterm.processor.ShortForecastV2Processor;
import com.softeer.batch.forecast.shortterm.reader.ShortForecastReader;
import com.softeer.batch.forecast.shortterm.writer.v2.ScheduledShortForecastV2Writer;
import com.softeer.batch.forecast.shortterm.writer.v2.StartUpShortForecastV2Writer;
import com.softeer.domain.Grid;
import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ThrottlingProperties;
import com.softeer.throttle.manager.impl.LeakyTokenThrottlingManager;
import io.github.bucket4j.distributed.proxy.ProxyManager;
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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.softeer.batch.common.support.BatchNames.Steps.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ShortForecastStepConfig {

    private static final int CHUNK_SIZE = 20;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ShortForecastReader shortForecastReader;
    private final ShortForecastV2Processor shortForecastProcessor;
    private final StartUpShortForecastV2Writer startShortForecastWriter;
    private final ScheduledShortForecastV2Writer scheduledShortForecastWriter;
    private final DailyTemperatureCollector dailyTemperatureCollector;
    private final ThrottlingProperties throttlingProperties;
    private final ProxyManager<String> proxyManager;

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

    @Bean(name = SHORT_FORECAST_V2_EX_SERVICE)
    public ExecutorService threadPoolV3() {
        return Executors.newFixedThreadPool(CHUNK_SIZE);
    }

    @Bean(name = SHORT_LEAKY_MANAGER)
    @StepScope
    public LeakyTokenThrottlingManager leakyTokenThrottlingManager() {
        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 400);
        return new LeakyTokenThrottlingManager(proxyManager, throttlingProperties, backoffStrategy);
    }

    private Step createForecastStep(String stepName, ItemWriter<CompletableFuture<ShortForecastList>> writer) {
        return new StepBuilder(stepName, jobRepository)
                .<Grid, CompletableFuture<ShortForecastList>>chunk(CHUNK_SIZE, transactionManager)
                .reader(shortForecastReader)
                .processor(shortForecastProcessor)
                .writer(writer)
                .listener(dailyTemperatureCollector)
                .build();
    }
}
