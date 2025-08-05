package com.softeer.team_6th.repository;

import com.softeer.team_6th.entity.GridEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * 그리드 JPA 리포지토리
 */
@Repository
public interface GridRepository extends JpaRepository<GridEntity, Long> {
    
    /**
     * 격자 좌표로 그리드 조회
     */
    Optional<GridEntity> findByNxAndNy(int nx, int ny);
    
    /**
     * 지역명으로 그리드 조회
     */
    Optional<GridEntity> findByRegionName(String regionName);
}