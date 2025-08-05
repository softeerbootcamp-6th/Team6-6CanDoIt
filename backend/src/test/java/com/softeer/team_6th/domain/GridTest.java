package com.softeer.team_6th.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Grid 도메인 테스트
 */
class GridTest {

    @Test
    void 그리드_도메인_생성_테스트() {
        // given
        Long id = 1L;
        int nx = 60;
        int ny = 127;
        String regionName = "서울";
        
        // when
        Grid grid = Grid.builder()
                .id(id)
                .nx(nx)
                .ny(ny)
                .regionName(regionName)
                .build();
        
        // then
        assertThat(grid.getId()).isEqualTo(id);
        assertThat(grid.getNx()).isEqualTo(nx);
        assertThat(grid.getNy()).isEqualTo(ny);
        assertThat(grid.getRegionName()).isEqualTo(regionName);
    }
}