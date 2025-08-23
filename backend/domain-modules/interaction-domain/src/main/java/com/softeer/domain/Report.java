package com.softeer.domain;

import com.softeer.entity.enums.EtceteraKeyword;
import com.softeer.entity.enums.RainKeyword;
import com.softeer.entity.enums.ReportType;
import com.softeer.entity.enums.WeatherKeyword;

import java.time.LocalDateTime;
import java.util.Set;

public record Report(long id, ReportType reportType, LocalDateTime createdAt,
                     long userId, String nickname, String userImageUrl,
                     String imageUrl, String content, int likeCount,
                     Set<WeatherKeyword> weatherKeywords, Set<RainKeyword> rainKeywords, Set<EtceteraKeyword> etceteraKeywords
                     ) {
}
