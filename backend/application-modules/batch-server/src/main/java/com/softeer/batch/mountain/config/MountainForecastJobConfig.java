package com.softeer.batch.mountain.config;

import com.softeer.batch.mountain.service.MountainForecastApiService;
import com.softeer.batch.mountain.tasklet.MountainForecastTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MountainForecastJobConfig {

    @Bean
    public MountainForecastTasklet mountainForecastTasklet(MountainForecastApiService apiService) {
        return new MountainForecastTasklet(apiService);
    }

    @Bean
    public Step mountainForecastStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            MountainForecastTasklet tasklet
    ) {
        return new StepBuilder("mountainForecastStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Job mountainForecastJob(
            JobRepository jobRepository,
            Step mountainForecastStep
    ) {
        return new JobBuilder("mountainForecastJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // 동일 파라미터로 재실행 허용
                .start(mountainForecastStep)
                .build();
    }
}
