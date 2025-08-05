package com.softeer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "daily_temperature")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DailyTemperatureEntity {

    @Id
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "highest")
    private double highestTemperature;

    @Column(name = "lowest")
    private double lowestTemperature;

    @OneToOne(fetch = FetchType.LAZY)
    private GridEntity gridEntity;
}
