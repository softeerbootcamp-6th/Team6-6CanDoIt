package com.softeer.team_6th.service;

import com.softeer.team_6th.domain.Grid;
import com.softeer.team_6th.entity.GridEntity;
import com.softeer.team_6th.repository.GridRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 그리드 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GridService {
    
    private final GridRepository gridRepository;
    
    /**
     * 모든 그리드 조회
     */
    public List<Grid> findAllGrids() {
        return gridRepository.findAll()
                .stream()
                .map(GridEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * ID로 그리드 조회
     */
    public Optional<Grid> findGridById(Long id) {
        return gridRepository.findById(id)
                .map(GridEntity::toDomain);
    }
    
    /**
     * 격자 좌표로 그리드 조회
     */
    public Optional<Grid> findGridByCoordinates(int nx, int ny) {
        return gridRepository.findByNxAndNy(nx, ny)
                .map(GridEntity::toDomain);
    }
    
    /**
     * 지역명으로 그리드 조회
     */
    public Optional<Grid> findGridByRegionName(String regionName) {
        return gridRepository.findByRegionName(regionName)
                .map(GridEntity::toDomain);
    }
    
    /**
     * 그리드 저장
     */
    @Transactional
    public Grid saveGrid(Grid grid) {
        GridEntity entity = GridEntity.fromDomain(grid);
        GridEntity savedEntity = gridRepository.save(entity);
        return savedEntity.toDomain();
    }
}