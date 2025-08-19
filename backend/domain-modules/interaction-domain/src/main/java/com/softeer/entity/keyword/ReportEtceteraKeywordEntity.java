package com.softeer.entity.keyword;


import com.softeer.entity.id.ReportEtceteraKeywordId;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "report_etcetera_keyword")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEtceteraKeywordEntity {
    @EmbeddedId
    private ReportEtceteraKeywordId id;
}