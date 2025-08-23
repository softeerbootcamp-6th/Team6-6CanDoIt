package com.softeer.batch.forecast.ultra;

import com.softeer.batch.forecast.ultra.v3.UltraForecastV3JobConfig;
import com.softeer.time.ApiTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.softeer.batch.forecast.ultra.v3.UltraForecastV3JobConfig.*;

@Slf4j
@Component
@Profile("!test")
public class UltraForecastJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job scheduledJob;

    public UltraForecastJobScheduler(
            JobLauncher jobLauncher,
            @Qualifier(SCHEDULED_ULTRA_FORECAST_V3_JOB) Job scheduledJob
    ) {
        this.jobLauncher = jobLauncher;
        this.scheduledJob = scheduledJob;
    }

    @Scheduled(cron = "30 5,15,25,35,45,55 * * * *", zone = "Asia/Seoul")
    public void runForecastJob() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = ApiTimeUtil.getBatchAlignedUltraBaseTime(now);
        JobParameters params = new JobParametersBuilder()
                .addLocalDateTime("dateTime", dateTime)
                .toJobParameters();

        log.info("Launching ScheduledUltraForecast Job at {}", now);
        JobExecution execution = jobLauncher.run(scheduledJob, params);
        log.info("ScheduledUltraForecastV1Job finished with status={}", execution.getStatus());
    }
}
