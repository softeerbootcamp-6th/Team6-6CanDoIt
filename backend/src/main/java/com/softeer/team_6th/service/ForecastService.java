package com.softeer.team_6th.service;

import com.softeer.team_6th.domain.Forecast;
import com.softeer.team_6th.entity.ForecastEntity;
import com.softeer.team_6th.repository.ForecastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 예보 서비스
 * 예보 관련 조회 기능을 제공
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ForecastService {
    
    private final ForecastRepository forecastRepository;
    
    /**
     * 모든 예보 조회
     */
    public List<Forecast> findAllForecasts() {
        return forecastRepository.findAll()
                .stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * ID로 예보 조회
     */
    public Optional<Forecast> findForecastById(Long id) {
        return forecastRepository.findById(id)
                .map(ForecastEntity::toDomain);
    }
    
    /**
     * 그리드 ID로 예보 조회
     */
    public List<Forecast> findForecastsByGridId(Long gridId) {
        return forecastRepository.findByGridId(gridId)
                .stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 그리드 ID와 특정 시간의 예보 조회
     */
    public List<Forecast> findForecastsByGridIdAndDateTime(Long gridId, LocalDateTime forecastDateTime) {
        return forecastRepository.findByGridIdAndForecastDateTime(gridId, forecastDateTime)
                .stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 그리드 ID와 카테고리로 예보 조회
     */
    public List<Forecast> findForecastsByGridIdAndCategory(Long gridId, String category) {
        return forecastRepository.findByGridIdAndCategory(gridId, category)
                .stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 그리드 ID와 시간 범위로 예보 조회
     */
    public List<Forecast> findForecastsByGridIdAndTimeRange(Long gridId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return forecastRepository.findByGridIdAndForecastDateTimeBetween(gridId, startDateTime, endDateTime)
                .stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 그리드 ID의 최신 예보 조회
     */
    public List<Forecast> findLatestForecastsByGridId(Long gridId) {
        return forecastRepository.findLatestForecastsByGridId(gridId)
                .stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 예보 저장
     */
    @Transactional
    public Forecast saveForecast(Forecast forecast) {
        ForecastEntity entity = ForecastEntity.fromDomain(forecast);
        ForecastEntity savedEntity = forecastRepository.save(entity);
        return savedEntity.toDomain();
    }
    
    /**
     * 여러 예보 일괄 저장
     */
    @Transactional
    public List<Forecast> saveAllForecasts(List<Forecast> forecasts) {
        List<ForecastEntity> entities = forecasts.stream()
                .map(ForecastEntity::fromDomain)
                .collect(Collectors.toList());
        
        List<ForecastEntity> savedEntities = forecastRepository.saveAll(entities);
        
        return savedEntities.stream()
                .map(ForecastEntity::toDomain)
                .collect(Collectors.toList());
    }
}