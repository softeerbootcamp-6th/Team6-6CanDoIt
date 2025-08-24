package com.softeer.batch.common.dto;

import java.time.Duration;
import java.util.Map;

public record RedisTtlWrite(
        byte[] key,
        Map<byte[], byte[]> value,
        Duration ttl
) {
}
