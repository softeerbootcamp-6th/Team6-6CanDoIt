package com.softeer.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchServerApplication.class, args);
    }

}
