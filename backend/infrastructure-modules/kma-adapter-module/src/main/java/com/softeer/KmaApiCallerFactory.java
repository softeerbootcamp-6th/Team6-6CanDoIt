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

    @SuppressWarnings("unchecked")
    public <T extends Record> KmaApiCaller<T, ?> getApiCaller(ForecastApiType forecastApiType, T request) {
        KmaApiCaller<?, ?> caller = callerMap.get(forecastApiType);
        if (caller == null) {
            throw new IllegalArgumentException("지원하지 않는 예보 타입입니다: " + forecastApiType);
        }

        Class<?> requestType = request.getClass();
        if (caller.getRequestType() != requestType) {
            throw new IllegalArgumentException("API Caller가 지원하지 않는 요청 타입입니다. Caller 지원 타입: "
                    + caller.getRequestType().getName() + ", 실제 요청 타입: " + requestType.getName());
        }

        return (KmaApiCaller<T, ?>) caller;
    }
}
