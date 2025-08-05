package com.softeer.domain.entity;

import com.softeer.entity.ImageEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "course")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int totalDistance;

    private int altitude;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private MountainEntity mountainEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;
}
