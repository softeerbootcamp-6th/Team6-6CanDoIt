package com.softeer.config;

import com.softeer.mountain.dto.MountainForecastApiRequest;
import com.softeer.mountain.dto.MountainForecastApiResponse;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public enum ForecastApiType {
    MOUNTAIN(new ParameterizedTypeReference<List<MountainForecastApiResponse>>() {}),
//    SHORT_TERM(),
//    ULTRA_SHORT_TERM()
    ;

    private final ParameterizedTypeReference<?> responseListType;

    ForecastApiType(ParameterizedTypeReference<?> responseListType) {
        this.responseListType = responseListType;
    }

    @SuppressWarnings("unchecked")
    public <T> ParameterizedTypeReference<List<T>> getResponseListType() {
        return (ParameterizedTypeReference<List<T>>) responseListType;
    }
}
