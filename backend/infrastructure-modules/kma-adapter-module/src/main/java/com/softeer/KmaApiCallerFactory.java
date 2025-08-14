package com.softeer;

import com.softeer.config.ForecastApiType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KmaApiCallerFactory {
    private final Map<ForecastApiType, KmaApiCaller<?, ?>> callerMap;

    public KmaApiCallerFactory(Set<KmaApiCaller<?, ?>> callers) {
        this.callerMap = callers.stream()
                .collect(Collectors.toUnmodifiableMap(KmaApiCaller::getForecastApiType, caller -> caller));
    }

    public KmaApiCaller<?, ?> getApiCaller(ForecastApiType forecastApiType) {
        KmaApiCaller<?, ?> caller = callerMap.get(forecastApiType);
        if (caller == null) {
            throw new IllegalArgumentException("지원하지 않는 예보 타입입니다: " + forecastApiType);
        }
        return caller;
    }
}
