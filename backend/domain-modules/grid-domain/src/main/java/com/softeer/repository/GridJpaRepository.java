package com.softeer.repository;

import com.softeer.entity.GridEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridJpaRepository extends JpaRepository<GridEntity, Integer> {
}
