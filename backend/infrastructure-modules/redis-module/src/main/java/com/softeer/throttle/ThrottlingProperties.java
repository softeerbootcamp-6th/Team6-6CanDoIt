package com.softeer.throttle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThrottlingProperties {

    private String key;
    private int initialTps;
    private int minTps;
    private int maxTps;
    private int failStep;

    public String key() {
        return key;
    }
    public int initialTps() {
        return initialTps;
    }

    public int minTps() {
        return minTps;
    }

    public int maxTps() {
        return maxTps;
    }

    public int failStep() {
        return failStep;
    }
}