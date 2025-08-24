package com.softeer.batch.forecast.chained.context;

import com.softeer.batch.forecast.chained.dto.DailyTemperatureKey;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureValue;
import org.springframework.batch.item.ExecutionContext;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DailyTemperatureContextAccessor {

    private DailyTemperatureContextAccessor() {}

    @SuppressWarnings("unchecked")
    public static Map<DailyTemperatureKey, DailyTemperatureValue> get(ExecutionContext ctx) {
        Object v = ctx.get(ExecutionContextKeys.DAILY_TEMP_MAP);
        if (v == null) return Collections.emptyMap();
        return (Map<DailyTemperatureKey, DailyTemperatureValue>) v;
    }

    public static void put(ExecutionContext ctx, Map<DailyTemperatureKey, DailyTemperatureValue> map) {
        ctx.put(ExecutionContextKeys.DAILY_TEMP_MAP, (Serializable) new HashMap<>(map));
    }
}
