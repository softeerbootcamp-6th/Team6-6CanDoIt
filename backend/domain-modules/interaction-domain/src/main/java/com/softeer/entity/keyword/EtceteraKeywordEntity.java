package com.softeer.entity.keyword;

import com.softeer.entity.enums.EtceteraKeyword;
import com.softeer.entity.enums.WeatherKeyword;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etcetera_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EtceteraKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private EtceteraKeyword keyword;
}
