package com.softeer.batch.forecast.mountain.scheduler;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MountainForecastJobScheduler {
    private static final Logger log = LoggerFactory.getLogger(MountainForecastJobScheduler.class);

    private final JobLauncher jobLauncher;
    private final Job startupJob;
    private final Job scheduledJob;

    public MountainForecastJobScheduler(JobLauncher jobLauncher,
                                        @Qualifier("startupMountainForecastJob") Job startupJob,
                                        @Qualifier("scheduledMountainForecastJob") Job scheduledJob) {
        this.jobLauncher = jobLauncher;
        this.startupJob = startupJob;
        this.scheduledJob = scheduledJob;
    }

    @PostConstruct
    public void runJobOnStartup() {
        try {
            log.info("Running startupForecastJob on application startup...");
            JobParameters params = new JobParametersBuilder()
                    .addString("startupAt", LocalDateTime.now().toString())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(startupJob, params); // <<< 시작용 Job 실행
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
        JobExecution execution = jobLauncher.run(scheduledJob, params);
        log.info("Job finished with status={}", execution.getStatus());
    }
}