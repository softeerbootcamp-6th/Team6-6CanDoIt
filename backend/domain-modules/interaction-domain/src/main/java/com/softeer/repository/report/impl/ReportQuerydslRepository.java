package com.softeer.repository.report.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.softeer.domain.Report;
import com.softeer.entity.QImageEntity;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.softeer.entity.QCourseEntity.*;
import static com.softeer.entity.QReportEntity.*;
import static com.softeer.entity.QReportLikeEntity.*;
import static com.softeer.entity.QUserEntity.*;
import static com.softeer.entity.keyword.QEtceteraKeywordEntity.*;
import static com.softeer.entity.keyword.QRainKeywordEntity.*;
import static com.softeer.entity.keyword.QReportEtceteraKeywordEntity.*;
import static com.softeer.entity.keyword.QReportRainKeywordEntity.*;
import static com.softeer.entity.keyword.QReportWeatherKeywordEntity.*;
import static com.softeer.entity.keyword.QWeatherKeywordEntity.*;

@Repository
@RequiredArgsConstructor
public class ReportQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Report> findReportsByCourseIdAndType(ReportPageable pageable, KeywordFilter keywordFilter,
                                                     long courseId, ReportType reportType) {

        List<Long> reportIds = selectReport()
                .where(
                        reportEntity.type.eq(reportType),
                        courseEntity.id.eq(courseId),
                        pageable.where(),
                        keywordFilter.where()
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();

        return loadReportsByIds(pageable, reportIds);
    }

    public List<Report> findMyReports(ReportPageable pageable, long userId) {

        List<Long> reportIds = selectReport()
                .innerJoin(userEntity).on(userEntity.id.eq(reportEntity.userId))
                .where(
                        pageable.where(),
                        reportEntity.userId.eq(userId)
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();



        return loadReportsByIds(pageable, reportIds);
    }

    public List<Report> findLikedReports(ReportPageable pageable, long userId) {

        List<Long> reportIds = selectReport()
                .innerJoin(userEntity).on(userEntity.id.eq(reportEntity.userId))
                .innerJoin(reportLikeEntity).on(reportEntity.id.eq(reportLikeEntity.id.reportId))
                .where(
                        pageable.where(),
                        reportLikeEntity.id.userId.eq(userId)
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();


        return loadReportsByIds(pageable, reportIds);
    }

    private JPAQuery<Long> selectReport() {
        return queryFactory
                .selectDistinct(reportEntity.id)
                .from(reportEntity)
                .innerJoin(courseEntity).on(courseEntity.id.eq(reportEntity.courseId))
                .leftJoin(reportEtceteraKeywordEntity).on(reportEntity.id.eq(reportEtceteraKeywordEntity.id.reportId))
                .leftJoin(etceteraKeywordEntity).on(reportEtceteraKeywordEntity.id.etceteraKeywordId.eq(etceteraKeywordEntity.id))
                .leftJoin(reportRainKeywordEntity).on(reportEntity.id.eq(reportRainKeywordEntity.id.reportId))
                .leftJoin(rainKeywordEntity).on(reportRainKeywordEntity.id.rainKeywordId.eq(rainKeywordEntity.id))
                .leftJoin(reportWeatherKeywordEntity).on(reportEntity.id.eq(reportWeatherKeywordEntity.id.reportId))
                .leftJoin(weatherKeywordEntity).on(reportWeatherKeywordEntity.id.weatherKeywordId.eq(weatherKeywordEntity.id));
    }

    private List<Report> loadReportsByIds(ReportPageable pageable, List<Long> reportIds) {
        if (reportIds.isEmpty()) {
            return Collections.emptyList();
        }

        QImageEntity reportImage = new QImageEntity("report_image");
        QImageEntity userImage = new QImageEntity("user_image");

        return queryFactory.from(reportEntity)
                .innerJoin(reportImage).on(reportImage.id.eq(reportEntity.imageId))
                .innerJoin(courseEntity).on(courseEntity.id.eq(reportEntity.courseId))
                .innerJoin(userEntity).on(userEntity.id.eq(reportEntity.userId))
                .leftJoin(userImage).on(userEntity.imageEntity.id.eq(userImage.id))
                .leftJoin(reportEtceteraKeywordEntity).on(reportEntity.id.eq(reportEtceteraKeywordEntity.id.reportId))
                .leftJoin(etceteraKeywordEntity).on(reportEtceteraKeywordEntity.id.etceteraKeywordId.eq(etceteraKeywordEntity.id))
                .leftJoin(reportRainKeywordEntity).on(reportEntity.id.eq(reportRainKeywordEntity.id.reportId))
                .leftJoin(rainKeywordEntity).on(reportRainKeywordEntity.id.rainKeywordId.eq(rainKeywordEntity.id))
                .leftJoin(reportWeatherKeywordEntity).on(reportEntity.id.eq(reportWeatherKeywordEntity.id.reportId))
                .leftJoin(weatherKeywordEntity).on(reportWeatherKeywordEntity.id.weatherKeywordId.eq(weatherKeywordEntity.id))
                .where(reportEntity.id.in(reportIds))
                .orderBy(pageable.orderBy())
                .transform(GroupBy.groupBy(reportEntity.id).list(
                        Projections.constructor(Report.class,
                                reportEntity.id,
                                reportEntity.type.as("reportType"),
                                reportEntity.createdAt,
                                userEntity.id,
                                userEntity.nickname,
                                userImage.imageUrl,
                                reportImage.imageUrl,
                                reportEntity.content,
                                reportEntity.likeCount,
                                GroupBy.set(weatherKeywordEntity.keyword),
                                GroupBy.set(rainKeywordEntity.keyword),
                                GroupBy.set(etceteraKeywordEntity.keyword)
                        )
                ));
    }
}
