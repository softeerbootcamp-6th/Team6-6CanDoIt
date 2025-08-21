package com.softeer.throttle.manager.impl;

import com.softeer.throttle.BackoffStrategy;
import com.softeer.throttle.ThrottlingProperties;
import com.softeer.throttle.manager.AbstractThrottlingManager;
import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class LeakyTokenThrottlingManager extends AbstractThrottlingManager {

    public LeakyTokenThrottlingManager(ProxyManager<String> proxyManager, ThrottlingProperties properties, BackoffStrategy backoffStrategy) {
        super(proxyManager, properties, backoffStrategy);
    }

    @Override
    protected Bandwidth createBandwidth() {
        return Bandwidth.builder()
                .capacity(1)
                .refillGreedy(currentTps.longValue(), Duration.ofSeconds(1))
                .initialTokens(0)
                .build();
    }
}