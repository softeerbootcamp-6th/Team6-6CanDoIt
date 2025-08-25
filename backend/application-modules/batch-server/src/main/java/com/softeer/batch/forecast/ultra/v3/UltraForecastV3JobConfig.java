package com.softeer.batch.forecast.ultra.v3;

import com.softeer.batch.forecast.shortterm.reader.ShortForecastReader;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.domain.Grid;
import com.softeer.shortterm.UltraForecastApiCaller;
import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ThrottlingProperties;
import com.softeer.throttle.manager.impl.FixedWindowThrottlingManager;
import com.softeer.throttle.manager.impl.LeakyTokenThrottlingManager;
import com.softeer.throttle.manager.impl.TokenBucketThrottlingManager;
import io.github.bucket4j.distributed.proxy.ProxyManager;
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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UltraForecastV3JobConfig {

    public static final String SCHEDULED_ULTRA_FORECAST_V3_JOB = "ScheduledUltraForecastV3Job";
    public static final String SCHEDULED_ULTRA_FORECAST_V3_STEP = SCHEDULED_ULTRA_FORECAST_V3_JOB + "Step";
    public static final String SCHEDULED_ULTRA_FORECAST_V3_EX_SERVICE = SCHEDULED_ULTRA_FORECAST_V3_STEP + "ExecutorService";

    public static final String KEY = "UltraForecastV3";
    public static final String SCHEDULED_ULTRA_FORECAST_V3_LEAKY_TOKEN = SCHEDULED_ULTRA_FORECAST_V3_STEP + "LeakyToken";
    public static final String SCHEDULED_ULTRA_FORECAST_V3_TOKEN_BUCKET = SCHEDULED_ULTRA_FORECAST_V3_STEP + "TokenBucket";
    public static final String SCHEDULED_ULTRA_FORECAST_V3_FIXED_WINDOW = SCHEDULED_ULTRA_FORECAST_V3_STEP + "FixedWindow";

    private static final int CHUNK_SIZE = 30;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ShortForecastReader shortForecastReader;
    private final UltraForecastV3Writer ultraForecastV3Writer;
    private final UltraForecastApiCaller  ultraForecastApiCaller;
    private final ForecastMapper  forecastMapper;

    private final ProxyManager<String> proxyManager;

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V3_JOB)
    public Job scheduledForecastJob() {
        return new JobBuilder(SCHEDULED_ULTRA_FORECAST_V3_JOB, jobRepository)
                .start(scheduledShortForecastStep())
                .build();
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V3_STEP)
    @JobScope
    public Step scheduledShortForecastStep() {
        return new StepBuilder(SCHEDULED_ULTRA_FORECAST_V3_STEP, jobRepository)
                .<Grid, CompletableFuture<UltraForecastResponseList>>chunk(CHUNK_SIZE, transactionManager)
                .reader(shortForecastReader)
                .processor(ultraForecastV3Processor(ultraForecastApiCaller, forecastMapper))
                .writer(ultraForecastV3Writer)
                .build();
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V3_EX_SERVICE)
    public ExecutorService threadPoolV3() {
        return Executors.newFixedThreadPool(30);
    }

    @Bean
    public ThrottlingProperties throttlingProperties() {
        return new ThrottlingProperties("UltraForecastV3", 20, 3, 30, 3);
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V3_LEAKY_TOKEN)
    @StepScope
    public LeakyTokenThrottlingManager leakyTokenThrottlingManager(ThrottlingProperties throttlingProperties) {
        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 400);
        return new LeakyTokenThrottlingManager(proxyManager, throttlingProperties, backoffStrategy);
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V3_TOKEN_BUCKET)
    @StepScope
    public TokenBucketThrottlingManager tokenBucketThrottlingManager(ThrottlingProperties throttlingProperties) {
        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 400);
        return new TokenBucketThrottlingManager(proxyManager, throttlingProperties, backoffStrategy);
    }

    @Bean(name = SCHEDULED_ULTRA_FORECAST_V3_FIXED_WINDOW)
    @StepScope
    public FixedWindowThrottlingManager fixedWindowThrottlingManager(ThrottlingProperties throttlingProperties) {
        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 400);
        return new FixedWindowThrottlingManager(proxyManager, throttlingProperties, backoffStrategy);
    }

    @Bean
    @StepScope
    public UltraForecastV3Processor ultraForecastV3Processor(UltraForecastApiCaller apiCaller,
                                                             ForecastMapper forecastMapper
                                                             ) {
        return new UltraForecastV3Processor(apiCaller, forecastMapper, threadPoolV3(), fixedWindowThrottlingManager(throttlingProperties()));
    }
}
