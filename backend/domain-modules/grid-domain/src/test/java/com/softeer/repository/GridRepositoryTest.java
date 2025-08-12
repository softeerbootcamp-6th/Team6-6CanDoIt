package com.softeer.repository;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.domain.Coordinate;
import com.softeer.domain.Grid;
import com.softeer.domain.GridFixture;
import com.softeer.entity.GridEntity;
import com.softeer.service.GridConverter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTestWithContainer
@Transactional
class GridRepositoryTest {

    @Autowired
    private GridJpaRepository gridJpaRepository;

    @Test
    @DisplayName("GridEntity를 저장하고 조회할 수 있어야 한다.")
    void save_shouldPersistAndFindEntity() {
        // Given
        Grid grid = GridFixture.builder()
                .id(0)
                .build();

        GridEntity gridEntity = GridEntity.from(grid);

        // When
        GridEntity savedEntity = gridJpaRepository.saveAndFlush(gridEntity);

        Optional<GridEntity> foundEntity = gridJpaRepository.findById(savedEntity.getId());

        // Then
        assertTrue(foundEntity.isPresent());
        assertEquals(grid.x(), foundEntity.get().getX());
        assertEquals(grid.y(), foundEntity.get().getY());
    }

    @Test
    @DisplayName("Grid 도메인 객체를 GridEntity로 올바르게 변환해야 한다.")
    void from_shouldMapDomainToEntity() {
        // Given
        Grid grid = GridFixture.createDefault();

        // When
        GridEntity gridEntity = GridEntity.from(grid);

        // Then
        assertNotNull(gridEntity);
        assertEquals(grid.id(), gridEntity.getId());
        assertEquals(grid.x(), gridEntity.getX());
        assertEquals(grid.y(), gridEntity.getY());
    }

    @Test
    @DisplayName("GridEntity를 Grid 도메인 객체로 올바르게 변환해야 한다.")
    void toDomainEntity_shouldMapEntityToDomain() {
        // Given
        // Fixture를 사용하여 Grid 도메인 객체 생성 후 Entity로 변환
        GridEntity gridEntity = GridEntity.from(GridFixture.createDefault());

        // When
        Grid grid = GridEntity.toDomainEntity(gridEntity);

        // Then
        assertNotNull(grid);
        assertEquals(gridEntity.getId(), grid.id());
        assertEquals(gridEntity.getX(), grid.x());
        assertEquals(gridEntity.getY(), grid.y());
    }

    @Test
    @DisplayName("위도, 경도를 기준으로 GridEntity를 조회할 수 있어야 한다.")
    void findByXAndY_shouldReturnEntity() {
        // Given
        Grid grid = GridFixture.builder().id(0).build();
        GridEntity gridEntity = GridEntity.from(grid);
        gridJpaRepository.saveAndFlush(gridEntity);

        // When
        Optional<GridEntity> foundEntity = gridJpaRepository.findByXAndY(grid.x(), grid.y());

        // Then
        assertTrue(foundEntity.isPresent());
        assertEquals(grid.x(), foundEntity.get().getX());
        assertEquals(grid.y(), foundEntity.get().getY());
    }

    @Test
    @DisplayName("위도, 경도를 기준으로 GridEntity를 조회할 때, 존재하지 않는 좌표는 빈 Optional을 반환해야 한다.")
    void findByXAndY_shouldThrowNotFoundException() {
        // Given
        double longitude = 126.9784;
        double latitude = 37.5665;

        // When
        Coordinate coordinate = GridConverter.convertWgsToCoordinate(longitude, latitude);

        // Then
        Optional<GridEntity> foundEntity = gridJpaRepository.findByXAndY(coordinate.x(), coordinate.y());
        assertTrue(foundEntity.isEmpty());
    }
}