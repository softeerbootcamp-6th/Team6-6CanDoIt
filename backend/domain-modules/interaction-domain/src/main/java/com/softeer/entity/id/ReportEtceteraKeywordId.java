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
public class ReportEtceteraKeywordId implements Serializable {
    @Column(name = "report_id")
    private long reportId;
    
    @Column(name = "etcetera_keyword_id")
    private int etceteraKeywordId;
}
