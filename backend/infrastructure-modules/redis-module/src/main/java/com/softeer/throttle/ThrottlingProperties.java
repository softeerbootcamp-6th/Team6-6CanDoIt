package com.softeer.throttle;

public record ThrottlingProperties(
    String key,
    int initialTps,
    int minTps,
    int maxTps,
    int failStep
) {
}