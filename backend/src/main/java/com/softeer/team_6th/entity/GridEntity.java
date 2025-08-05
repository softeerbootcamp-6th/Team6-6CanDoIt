package com.softeer.team_6th.entity;

import com.softeer.team_6th.domain.Grid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그리드 JPA 엔티티
 * 기상청 격자 좌표계 정보를 저장하는 엔티티
 */
@Entity
@Table(name = "grids")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GridEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int nx;  // 격자 X 좌표
    
    @Column(nullable = false)
    private int ny;  // 격자 Y 좌표
    
    @Column(name = "region_name", nullable = false)
    private String regionName;  // 지역명
    
    @Builder
    public GridEntity(int nx, int ny, String regionName) {
        this.nx = nx;
        this.ny = ny;
        this.regionName = regionName;
    }
    
    /**
     * JPA 엔티티를 도메인 객체로 변환
     */
    public Grid toDomain() {
        return Grid.builder()
                .id(this.id)
                .nx(this.nx)
                .ny(this.ny)
                .regionName(this.regionName)
                .build();
    }
    
    /**
     * 도메인 객체로부터 JPA 엔티티 생성
     */
    public static GridEntity fromDomain(Grid grid) {
        return GridEntity.builder()
                .nx(grid.getNx())
                .ny(grid.getNy())
                .regionName(grid.getRegionName())
                .build();
    }
}