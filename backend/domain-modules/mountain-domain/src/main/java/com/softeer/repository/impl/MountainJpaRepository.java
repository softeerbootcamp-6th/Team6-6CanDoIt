package com.softeer.repository.impl;

import com.softeer.entity.MountainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MountainJpaRepository extends JpaRepository<MountainEntity, Long> {

    @Query("SELECT m " +
            "FROM MountainEntity m " +
            "JOIN FETCH GridEntity g ON g.id = m.gridEntity.id " +
            "JOIN FETCH ImageEntity i ON i.id = m.imageEntity.id")
    List<MountainEntity> findAll();
}
