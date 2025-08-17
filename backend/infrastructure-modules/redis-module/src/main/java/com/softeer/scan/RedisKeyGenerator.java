package com.softeer.scan;

import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class RedisKeyGenerator {

    private static final String DELIMITER = ":";
    private static final String ALL = "*";

    public String generateKey(String prefix, Object... args) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(prefix);
        for (Object arg : args) {
            joiner.add(arg.toString());
        }
        return joiner.toString();
    }

    public String generateScanPattern(String prefix, Object... args) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(prefix);
        for (Object arg : args) {
            joiner.add(arg.toString());
        }
        joiner.add(ALL);
        return joiner.toString();
    }
}
