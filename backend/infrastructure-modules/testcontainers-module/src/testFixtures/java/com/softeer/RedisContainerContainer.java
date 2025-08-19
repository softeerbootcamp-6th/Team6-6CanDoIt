package com.softeer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class RedisContainerContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String REDIS_DOCKER_IMAGE = "redis:7.0.3-alpine";

    @Container
    static GenericContainer<?> LETTUCE_CONTAINER = new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE))
            .withExposedPorts(6379)
            .withReuse(true);

    static {
        if (!LETTUCE_CONTAINER.isRunning()) LETTUCE_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.redis.host=" + LETTUCE_CONTAINER.getHost(),
                "spring.redis.port=" + LETTUCE_CONTAINER.getFirstMappedPort(),
                "spring.redis.username=",          // redis 5.x는 ACL username 미지원 → 빈 값
                "spring.redis.password=",          // 패스워드 미사용
                "spring.redis.ssl=false"           // SSL 미사용
        ).applyTo(applicationContext.getEnvironment());
    }
}
