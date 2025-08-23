package com.softeer.throttle.manager.impl;

import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ThrottlingProperties;
import com.softeer.throttle.manager.AbstractThrottlingManager;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.distributed.proxy.ProxyManager;

import java.time.Duration;

public class TokenBucketThrottlingManager extends AbstractThrottlingManager {

    public TokenBucketThrottlingManager(ProxyManager<String> proxyManager, ThrottlingProperties properties, BackoffStrategy backoffStrategy) {
        super(proxyManager, properties, backoffStrategy);
    }

    @Override
    protected Bandwidth createBandwidth() {
        return Bandwidth.builder()
                .capacity(properties.maxTps())
                .refillGreedy(currentTps.longValue(), Duration.ofSeconds(1))
                .initialTokens(properties.initialTps())
                .build();
    }
}
