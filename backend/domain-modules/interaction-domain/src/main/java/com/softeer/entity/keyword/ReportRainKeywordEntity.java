package com.softeer.entity.keyword;

import com.softeer.entity.id.ReportRainKeywordId;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "report_rain_keyword")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRainKeywordEntity {
    @EmbeddedId
    private ReportRainKeywordId id;
}