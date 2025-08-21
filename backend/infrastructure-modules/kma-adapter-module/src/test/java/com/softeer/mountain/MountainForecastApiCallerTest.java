package com.softeer.mountain;

import com.softeer.mountain.dto.MountainForecastApiRequest;
import com.softeer.mountain.dto.MountainForecastApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MountainForecastApiCallerTest {

    private MountainForecastApiCaller target;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        target = new MountainForecastApiCaller(webClient);
    }

    @Test
    @DisplayName("산악 날씨 API를 성공적으로 호출하고 응답 데이터를 파싱해야 한다")
    void call_api_successfully_and_parse_response() {
        // given
        MountainForecastApiRequest request = MountainForecastApiRequestFixture.createDefault();

        MountainForecastApiResponse apiResponse1 = MountainForecastApiResponseFixture.builder()
                .category("TMP")
                .forecastValue("25")
                .build();
        MountainForecastApiResponse apiResponse2 = MountainForecastApiResponseFixture.builder()
                .category("POP")
                .forecastValue("50")
                .build();

        List<MountainForecastApiResponse> expectedResponseList = List.of(apiResponse1, apiResponse2);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ArgumentMatchers.<ParameterizedTypeReference<List<MountainForecastApiResponse>>>any()).block())
                .thenReturn(expectedResponseList);

        // when
        List<MountainForecastApiResponse> actualResponse = target.call(request);

        // then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse).hasSize(2);
        assertThat(actualResponse).isEqualTo(expectedResponseList);

        MountainForecastApiResponse firstItem = actualResponse.get(0);
        assertThat(firstItem.forecastValue()).isEqualTo("25");
        assertThat(firstItem.category()).isEqualTo("TMP");
    }
}