package com.softeer.repository.impl;

import com.softeer.entity.MountainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainJpaRepository extends JpaRepository<MountainEntity, Long> {
}
