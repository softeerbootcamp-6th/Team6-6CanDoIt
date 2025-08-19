package com.softeer.domain;

import com.softeer.entity.enums.EtceteraKeyword;
import com.softeer.entity.enums.RainKeyword;
import com.softeer.entity.enums.ReportType;
import com.softeer.entity.enums.WeatherKeyword;

import java.time.LocalDateTime;
import java.util.List;

public record Report(long id, ReportType reportType, LocalDateTime createdAt,
                     long userId, String nickname, String userImageUrl,
                     String imageUrl, String content, int likeCount,
                     List<WeatherKeyword> weatherKeywords, List<RainKeyword> rainKeywords, List<EtceteraKeyword> etceteraKeywords
                     ) {
}
