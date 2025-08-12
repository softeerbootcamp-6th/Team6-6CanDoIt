package com.softeer.repository;

import com.softeer.entity.GridEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GridJpaRepository extends JpaRepository<GridEntity, Integer> {

    Optional<GridEntity> findByXAndY(int x, int y);
}
