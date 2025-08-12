package com.softeer.service;

import com.softeer.domain.Coordinate;
import com.softeer.domain.Grid;
import com.softeer.entity.GridEntity;
import com.softeer.error.ExceptionCreator;
import com.softeer.exception.GridException;
import com.softeer.repository.GridJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GridUseCase {

    private final GridJpaRepository gridJpaRepository;

    public Grid getGridByLatitudeAndLongitude(double latitude, double longitude) {
        Coordinate coordinate = GridConverter.convertWgsToCoordinate(longitude, latitude);

        GridEntity gridEntity = gridJpaRepository.findByXAndY(coordinate.x(), coordinate.y())
                .orElseThrow(() -> ExceptionCreator.create(GridException.NOT_FOUND));

        return GridEntity.toDomainEntity(gridEntity);
    }
}
