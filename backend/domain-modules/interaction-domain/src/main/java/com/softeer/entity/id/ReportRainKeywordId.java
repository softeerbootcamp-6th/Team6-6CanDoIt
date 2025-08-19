package com.softeer.entity.id;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReportRainKeywordId implements Serializable {
    @Column(name = "report_id")
    private long reportId;
    
    @Column(name = "rain_keyword_id")
    private int rainKeywordId;
}