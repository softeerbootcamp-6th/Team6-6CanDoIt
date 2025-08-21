package com.softeer.throttle.manager.impl;

import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ThrottlingProperties;
import com.softeer.throttle.manager.AbstractThrottlingManager;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.distributed.proxy.ProxyManager;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FixedWindowThrottlingManager extends AbstractThrottlingManager {

    public FixedWindowThrottlingManager(ProxyManager<String> proxyManager, ThrottlingProperties properties, BackoffStrategy backoffStrategy) {
        super(proxyManager, properties, backoffStrategy);
    }

    @Override
    protected Bandwidth createBandwidth() {
        long tps = currentTps.longValue();

        Instant firstRefill = Instant.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(1);

        return Bandwidth.builder()
                .capacity(tps)
                .refillIntervallyAligned(tps, Duration.ofSeconds(1), firstRefill)
                .initialTokens(0)
                .build();
    }
}
