package com.softeer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "card_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cousre_id")
    private long courseId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "forecast_date", nullable = false)
    private LocalDateTime forecastDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
