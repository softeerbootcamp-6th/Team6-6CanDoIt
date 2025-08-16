package com.softeer.shortterm.dto.response;

import com.softeer.shortterm.ShortForecastApiResponseFixture;
import com.softeer.shortterm.ShortForecastHeaderFixture;
import com.softeer.shortterm.ShortForecastItemFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortForecastApiResponseTest {

    @Test
    @DisplayName("getHeader()는 올바른 Header 객체를 반환한다")
    void getHeader_shouldReturnCorrectHeader() {
        // given
        ShortForecastHeader expectedHeader = ShortForecastHeaderFixture.builder()
                .resultCode("00")
                .resultMsg("NORMAL_SERVICE")
                .build();
        ShortForecastApiResponse response = ShortForecastApiResponseFixture.builder()
                .header(expectedHeader)
                .build();

        // when
        ShortForecastHeader actualHeader = response.getHeader();

        // then
        assertThat(actualHeader).isNotNull();
        assertThat(actualHeader.resultCode()).isEqualTo("00");
        assertThat(actualHeader).isEqualTo(expectedHeader);
    }

    @Test
    @DisplayName("getBody()는 올바른 Body 객체를 반환한다")
    void getBody_shouldReturnCorrectBody() {
        // given
        List<ShortForecastItem> itemList = List.of(
                ShortForecastItemFixture.builder().category("TMP").build(),
                ShortForecastItemFixture.builder().category("POP").build(),
                ShortForecastItemFixture.builder().category("SKY").build()
        );
        ShortForecastApiResponse response = ShortForecastApiResponseFixture.builder()
                .items(itemList)
                .build();

        // when
        ShortForecastBody actualBody = response.getBody();

        // then
        assertThat(actualBody).isNotNull();
        assertThat(actualBody.totalCount()).isEqualTo(3);
        assertThat(actualBody.shortForecastItems().shortForecastItemList()).hasSize(3);
    }

    @Test
    @DisplayName("Body의 getItems()는 Item 리스트를 직접 반환한다")
    void getItems_fromBody_shouldReturnItemListDirectly() {
        // given
        ShortForecastItem item1 = ShortForecastItemFixture.builder().category("TMP").forecastValue("28").build();
        ShortForecastItem item2 = ShortForecastItemFixture.builder().category("REH").forecastValue("70").build();
        List<ShortForecastItem> expectedItems = List.of(item1, item2);

        ShortForecastApiResponse response = ShortForecastApiResponseFixture.builder()
                .items(expectedItems)
                .build();
        ShortForecastBody body = response.getBody();

        // when
        List<ShortForecastItem> actualItems = body.getItems();

        // then
        assertThat(actualItems)
                .isNotNull()
                .hasSize(2)
                .containsExactlyElementsOf(expectedItems);
    }

    @Test
    @DisplayName("Body의 getItems()는 아이템이 없을 때 빈 리스트를 반환한다")
    void getItems_whenNoItems_shouldReturnEmptyList() {
        // given
        ShortForecastApiResponse response = ShortForecastApiResponseFixture.builder()
                .items(Collections.EMPTY_LIST).build();
        ShortForecastBody body = response.getBody();

        // when
        List<ShortForecastItem> actualItems = body.getItems();

        // then
        assertThat(actualItems).isNotNull().isEmpty();
    }
}
