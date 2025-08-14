package com.softeer;

import com.softeer.config.ForecastApiType;
import com.softeer.mountain.MountainForecastApiCaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Mockito 사용을 위한 어노테이션
class KmaApiCallerFactoryTest {

    private KmaApiCallerFactory kmaApiCallerFactory;

    @Mock
    private MountainForecastApiCaller mountainForecastApiCaller;

    @BeforeEach
    void setUp() {
        when(mountainForecastApiCaller.getForecastApiType()).thenReturn(ForecastApiType.MOUNTAIN);
        kmaApiCallerFactory = new KmaApiCallerFactory(Set.of(mountainForecastApiCaller));
    }

    @Test
    @DisplayName("Factory가 MOUNTAIN 타입에 맞는 ApiCaller를 정상적으로 반환한다.")
    void getApiCaller_success() {
        // when
        KmaApiCaller<?, ?> caller = kmaApiCallerFactory.getApiCaller(ForecastApiType.MOUNTAIN);

        // then
        assertThat(caller).isNotNull();
        assertThat(caller).isInstanceOf(MountainForecastApiCaller.class);
    }
}