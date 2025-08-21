package com.softeer.batch.forecast.chained.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.softeer.batch.forecast.support.BatchNames.Jobs.SCHEDULED_CHAINED_FORECAST_JOB;
import static com.softeer.batch.forecast.support.BatchNames.Jobs.STARTUP_CHAINED_FORECAST_JOB;

@Slf4j
@Component
@Profile("!test")
public class ChainedJobScheduler {

    private final JobLauncher jobLauncher;

    private final Job chainedStartupJob;
    private final Job chainedScheduledJob;

    public ChainedJobScheduler(
            JobLauncher jobLauncher,
            @Qualifier(STARTUP_CHAINED_FORECAST_JOB) Job chainedStartupJob,
            @Qualifier(SCHEDULED_CHAINED_FORECAST_JOB) Job chainedScheduledJob
    ) {
        this.jobLauncher = jobLauncher;
        this.chainedStartupJob = chainedStartupJob;
        this.chainedScheduledJob = chainedScheduledJob;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runJobOnStartup() {
        try {
            log.info("Running startupForecastJob on application startup...");
            LocalDateTime now = LocalDateTime.now();

            JobParameters params = new JobParametersBuilder()
                    .addString("startupAt", now.toString())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(chainedStartupJob, params);
            log.info("Startup job finished with status={}", execution.getStatus());
        } catch (Exception e) {
            log.error("Failed to run startupForecastJob on startup", e);
        }
    }

    /**
     * 매일 2, 5, 8, 11, 14, 17, 20, 23시 정각 실행 (Asia/Seoul)
     * 초 분 시 일 월 요일
     */
    @Scheduled(cron = "0 2 2,5,8,11,14,17,20,23 * * *", zone = "Asia/Seoul")
    public void runForecastJob() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        JobParameters params = new JobParametersBuilder()
                .addString("scheduledAt", now.toString())
                .addLong("unique", System.currentTimeMillis())
                .toJobParameters();

        log.info("Launching mountainForecastJob at {}", now);
        JobExecution execution = jobLauncher.run(chainedScheduledJob, params);
        log.info("ScheduledMountainForecastJob finished with status={}", execution.getStatus());
    }
}
