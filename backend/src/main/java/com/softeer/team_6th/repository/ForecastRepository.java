package com.softeer.team_6th.repository;

import com.softeer.team_6th.entity.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 예보 JPA 리포지토리
 */
@Repository
public interface ForecastRepository extends JpaRepository<ForecastEntity, Long> {
    
    /**
     * 그리드 ID로 예보 조회
     */
    List<ForecastEntity> findByGridId(Long gridId);
    
    /**
     * 그리드 ID와 예보 시간으로 예보 조회
     */
    List<ForecastEntity> findByGridIdAndForecastDateTime(Long gridId, LocalDateTime forecastDateTime);
    
    /**
     * 그리드 ID와 카테고리로 예보 조회
     */
    List<ForecastEntity> findByGridIdAndCategory(Long gridId, String category);
    
    /**
     * 그리드 ID와 예보 시간 범위로 예보 조회
     */
    @Query("SELECT f FROM ForecastEntity f WHERE f.gridId = :gridId AND f.forecastDateTime BETWEEN :startDateTime AND :endDateTime ORDER BY f.forecastDateTime")
    List<ForecastEntity> findByGridIdAndForecastDateTimeBetween(
            @Param("gridId") Long gridId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
    
    /**
     * 최신 예보 기준시간의 예보 조회
     */
    @Query("SELECT f FROM ForecastEntity f WHERE f.gridId = :gridId AND f.baseDateTime = (SELECT MAX(f2.baseDateTime) FROM ForecastEntity f2 WHERE f2.gridId = :gridId)")
    List<ForecastEntity> findLatestForecastsByGridId(@Param("gridId") Long gridId);
}