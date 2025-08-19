package com.softeer.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReportLikeId implements Serializable {
    @Column(name = "report_id")
    private long reportId;
    
    @Column(name = "user_id")
    private long userId;
}