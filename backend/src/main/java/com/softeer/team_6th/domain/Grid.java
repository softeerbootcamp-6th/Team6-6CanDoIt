package com.softeer.team_6th.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 그리드 도메인 객체
 * 기상청 격자 좌표계를 나타내는 도메인
 */
@Getter
@Builder
public class Grid {
    private final Long id;
    private final int nx;  // 격자 X 좌표
    private final int ny;  // 격자 Y 좌표
    private final String regionName;  // 지역명
    
    public Grid(Long id, int nx, int ny, String regionName) {
        this.id = id;
        this.nx = nx;
        this.ny = ny;
        this.regionName = regionName;
    }
}