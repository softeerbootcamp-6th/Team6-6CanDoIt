package com.softeer.throttle;

import com.softeer.SpringBootTestWithRedis;
import com.softeer.throttle.manager.impl.LeakyTokenThrottlingManager;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTestWithRedis
class LeakyTokenThrottlingManagerTest {

    private static final Logger log = LoggerFactory.getLogger(LeakyTokenThrottlingManagerTest.class);

    private LeakyTokenThrottlingManager leakyTokenThrottlingManager;
    @Autowired
    private ProxyManager<String> proxyManager;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StatefulRedisConnection<String, byte[]> redisConnection;
    private ThrottlingProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //(10, 20) -> 18초
        //(5, 10) ->
        properties = new ThrottlingProperties(
                "test-key",
                3,     // initialTps
                2,     // minTps
                30,    // maxTps
                5
        );

        BackoffStrategy backoffStrategy = new BackoffStrategy(100, 500);

        leakyTokenThrottlingManager = new LeakyTokenThrottlingManager(proxyManager, properties, backoffStrategy);
        leakyTokenThrottlingManager.initialize();

        // Redis 정리
        redisConnection.sync().flushall();
        proxyManager.removeProxy("test-key");
    }

    @Test
    @DisplayName("정상적인 작업 처리 테스트")
    void testSuccessfulTaskExecution() throws Exception {
        // given
        CompletableFuture<String> task = CompletableFuture.completedFuture("success");

        // when
        CompletableFuture<String> result = leakyTokenThrottlingManager.submit("test-key", () -> task);

        // then
        assertEquals("success", result.get(1, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("TPS 제한 초과 시 재시도 테스트")
    void testTpsExceededRetry() throws Exception {
        // given
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        int requestCount = 30; // initialTps(5)보다 많은 요청

        int expected = 0;
        // when
        for (int i = 0; i < requestCount; i++) {
            final int index = i;
            expected += i;
            CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return index;
            });

            futures.add(leakyTokenThrottlingManager.submit("test-key", () -> task));
        }

        // then
        List<Integer> results = new ArrayList<>();
        for (CompletableFuture<Integer> future : futures) {
            results.add(future.get(5, TimeUnit.SECONDS));
        }

        Integer sum = results.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        assertEquals(requestCount, results.size());
        assertEquals(expected, sum);
        log.info("모든 작업이 완료되었습니다. 결과 개수: {}", results.size());
    }

    @Test
    @DisplayName("비동기 작업 예외 처리 테스트")
    void testAsyncTaskException() throws InterruptedException {
        // given
        CompletableFuture<String> failingTask = new CompletableFuture<>();
        failingTask.completeExceptionally(new RuntimeException("작업 실패"));

        // when
        CompletableFuture<String> result = leakyTokenThrottlingManager.submit("test-key", () -> failingTask);

        // then
        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            result.get(1, TimeUnit.SECONDS);
        });


        Thread.sleep(2000);
        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("작업 실패", exception.getCause().getMessage());
    }
}