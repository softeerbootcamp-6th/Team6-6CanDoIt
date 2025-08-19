package com.softeer.entity;

import com.softeer.entity.id.ReportLikeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_like")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportLikeEntity {
    @EmbeddedId
    private ReportLikeId id;

}