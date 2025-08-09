package com.softeer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class TestcontainersConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String MYSQL_IMAGE = "mysql:8.0";
    private static final String DB_NAME = "testdb";
    private static final String DB_USER = "testuser";
    private static final String DB_PASSWORD = "testpass";

    @Container
    static MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(MYSQL_IMAGE)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USER)
            .withPassword(DB_PASSWORD)
            .withReuse(true);

    static {
        if (!MYSQL_CONTAINER.isRunning()) {
            MYSQL_CONTAINER.start();
        }
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        TestPropertyValues.of(
                "spring.datasource.url=" + MYSQL_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + MYSQL_CONTAINER.getUsername(),
                "spring.datasource.password=" + MYSQL_CONTAINER.getPassword(),

                "spring.jpa.hibernate.ddl-auto=update",

                // JPA 엔티티가 모두 초기화된 후에 데이터소스 초기화를 지연
                "spring.jpa.defer-datasource-initialization=true",


                // JPA/Hibernate 로깅 설정
                "spring.jpa.show-sql=true",
                "spring.jpa.properties.hibernate.format_sql=true"
        ).applyTo(applicationContext.getEnvironment());

    }
}