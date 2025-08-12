package com.softeer.repository;

import com.softeer.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {
}
