package com.softeer.shortterm.dto.request;

import com.softeer.shortterm.ShortForecastApiRequestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShortForecastApiRequestTest {

    @Test
    @DisplayName("queryString 메서드는 모든 파라미터를 올바른 키와 함께 문자열로 조립한다")
    void queryString_shouldAssembleAllParametersIntoCorrectString() {
        // given
        ShortForecastApiRequest request = ShortForecastApiRequestFixture.createDefault();

        // when
        String queryString = request.queryString();

        // then
        String expectedQueryString = "serviceKey=TEST_SERVICE_KEY" +
                "&pageNo=1" +
                "&numOfRows=100" +
                "&dataType=JSON" +
                "&base_date=20250816" +
                "&base_time=0500" +
                "&nx=60" +
                "&ny=127";

        assertThat(queryString).isEqualTo(expectedQueryString);
    }

    @Test
    @DisplayName("serviceKey에 특수문자가 포함된 경우, 올바르게 URL 인코딩된다")
    void queryString_shouldUrlEncodeServiceKeyWithSpecialCharacters() {
        // given
        String specialServiceKey = "a/b+c=d";
        ShortForecastApiRequest request = ShortForecastApiRequestFixture.builder()
                .serviceKey(specialServiceKey)
                .build();

        // when
        String actualQueryString = request.queryString();

        // then
        String expectedEncodedKey = "a%2Fb%2Bc%3Dd";

        assertThat(actualQueryString).startsWith("serviceKey=" + expectedEncodedKey);
        assertThat(actualQueryString).contains("&pageNo=1");
    }
}