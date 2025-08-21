package com.softeer.throttle;

import com.softeer.throttle.ex.ThrottleException;
import com.softeer.throttle.ex.ThrottleExceptionStatus;
import com.softeer.throttle.manager.ThrottlingManager;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThrottlingManagerRetryTest {

    ThrottlingManager manager;

    @AfterEach
    void tearDown() {
        if (manager != null) manager.shutdown(); // @PreDestroy
    }

    @Test
    void retryable_then_succeeds_on_nth_attempt() throws Exception {

        ProxyManager<String> pm = mock(ProxyManager.class); // 실제 Redis 없이 진행
        ThrottlingProperties props = mock(ThrottlingProperties.class);
        when(props.initialTps()).thenReturn(30);
        when(props.maxTps()).thenReturn(100);
        when(props.minTps()).thenReturn(1);
        when(props.failStep()).thenReturn(5);

        BackoffStrategy backoff = new BackoffStrategy(10, 30);// 10ms, 20ms, 40ms...

        manager = new ThrottlingManager(pm, props, backoff);
        manager.initialize();

        Bucket bucket = mock(Bucket.class);
        ConsumptionProbe consumed = mock(ConsumptionProbe.class);
        when(consumed.isConsumed()).thenReturn(true);
        when(bucket.tryConsumeAndReturnRemaining(1)).thenReturn(consumed);

        injectBucket(manager, bucket);

        final int succeedOn = 4; // 1~3회 실패, 4번째 성공
        AtomicInteger attempt = new AtomicInteger(0);
        Supplier<CompletableFuture<String>> flakySupplier = () -> {
            int a = attempt.incrementAndGet();
            if (a < succeedOn) {
                CompletableFuture<String> f = new CompletableFuture<>();
                f.completeExceptionally(new ThrottleException(ThrottleExceptionStatus.RETRY));
                return f;
            }
            return CompletableFuture.completedFuture("OK@" + a);
        };

        CompletableFuture<String> future = manager.submit("KMA:GLOBAL", flakySupplier);

        String result = future.get(3, TimeUnit.SECONDS); // 충분한 타임아웃
        assertThat(result).isEqualTo("OK@4");
        assertThat(attempt.get()).isEqualTo(succeedOn); // 정확히 4회 호출되었는지
    }

    @Test
    void non_retryable_then_fails_immediately() throws Exception {
        ProxyManager<String> pm = mock(ProxyManager.class);
        ThrottlingProperties props = mock(ThrottlingProperties.class);
        when(props.initialTps()).thenReturn(30);
        when(props.maxTps()).thenReturn(100);
        when(props.minTps()).thenReturn(1);
        when(props.failStep()).thenReturn(5);

        BackoffStrategy backoff = new BackoffStrategy(10, 30);// 10ms, 20ms, 40ms...


        manager = new ThrottlingManager(pm, props, backoff);
        manager.initialize();

        Bucket bucket = mock(Bucket.class);
        ConsumptionProbe consumed = mock(ConsumptionProbe.class);
        when(consumed.isConsumed()).thenReturn(true);
        when(bucket.tryConsumeAndReturnRemaining(1)).thenReturn(consumed);
        injectBucket(manager, bucket);

        AtomicInteger attempt = new AtomicInteger(0);
        Supplier<CompletableFuture<String>> nonRetryable = () -> {
            attempt.incrementAndGet();
            CompletableFuture<String> f = new CompletableFuture<>();
            f.completeExceptionally(new ThrottleException(ThrottleExceptionStatus.NO_RETRY));
            return f;
        };

        CompletableFuture<String> future = manager.submit("KMA:GLOBAL", nonRetryable);

        // 즉시 실패(재시도 없음)
        assertThrows(Exception.class, () -> future.get(1, TimeUnit.SECONDS));
        assertThat(attempt.get()).isEqualTo(1);
    }

    private static void injectBucket(ThrottlingManager manager, Bucket bucket) throws Exception {
        Field f = ThrottlingManager.class.getDeclaredField("bucket");
        f.setAccessible(true);
        f.set(manager, bucket);
    }
}
