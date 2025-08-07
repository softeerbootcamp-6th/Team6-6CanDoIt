package com.softeer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class TestcontainersConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static {
        System.setProperty("testcontainers.reuse.enable", "true");
    }

    @Container
    static MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withReuse(true)
            .withLabel("reuse-group", "integration-tests");

    static {
        MYSQL_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + MYSQL_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + MYSQL_CONTAINER.getUsername(),
                "spring.datasource.password=" + MYSQL_CONTAINER.getPassword(),
                "spring.jpa.hibernate.ddl-auto=create-drop",  // 변경
                "spring.jpa.defer-datasource-initialization=false"
        ).applyTo(applicationContext.getEnvironment());
    }
}