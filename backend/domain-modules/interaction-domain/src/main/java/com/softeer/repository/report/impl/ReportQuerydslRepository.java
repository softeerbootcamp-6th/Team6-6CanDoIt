package com.softeer.repository.report.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.softeer.domain.Report;
import com.softeer.entity.QImageEntity;
import com.softeer.entity.enums.ReportType;
import com.softeer.entity.keyword.QReportEtceteraKeywordEntity;
import com.softeer.entity.keyword.QReportRainKeywordEntity;
import com.softeer.entity.keyword.QReportWeatherKeywordEntity;
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
                                                     long courseId, ReportType reportType, long userId) {

        JPAQuery<Long> query = queryFactory.selectDistinct(reportEntity.id)
                .from(reportEntity)
                .innerJoin(courseEntity).on(courseEntity.id.eq(reportEntity.courseId));

        addDynamicKeywordJoins(query, keywordFilter);

        List<Long> reportIds = query.where(
                        reportEntity.type.eq(reportType),
                        courseEntity.id.eq(courseId),
                        pageable.where()
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();

        return loadReportsByIds(pageable, reportIds, userId);
    }

    public List<Report> findMyReports(ReportPageable pageable, long userId) {

        List<Long> reportIds = selectReportIds()
                .innerJoin(userEntity).on(userEntity.id.eq(reportEntity.userId))
                .where(
                        pageable.where(),
                        reportEntity.userId.eq(userId)
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();

        return loadReportsByIds(pageable, reportIds, userId);
    }

    public List<Report> findLikedReports(ReportPageable pageable, long userId) {

        List<Long> reportIds = selectReportIds()
                .innerJoin(userEntity).on(userEntity.id.eq(reportEntity.userId))
                .innerJoin(reportLikeEntity).on(reportEntity.id.eq(reportLikeEntity.id.reportId))
                .where(
                        pageable.where(),
                        reportLikeEntity.id.userId.eq(userId)
                )
                .orderBy(pageable.orderBy())
                .limit(pageable.pageSize())
                .fetch();


        return loadReportsByIds(pageable, reportIds, userId);
    }

    private void addDynamicKeywordJoins(JPAQuery<Long> query, KeywordFilter keywordFilter) {
        List<Integer> weatherIds = keywordFilter.weatherKeywordIds();
        if (!weatherIds.isEmpty()) {
            for (int i = 0; i < weatherIds.size(); i++) {
                QReportWeatherKeywordEntity qWke = new QReportWeatherKeywordEntity("wke" + i);
                query.innerJoin(qWke).on(qWke.id.reportId.eq(reportEntity.id).and(qWke.id.weatherKeywordId.eq(weatherIds.get(i))));
            }
        }

        List<Integer> rainIds = keywordFilter.rainKeywordIds();
        if (!rainIds.isEmpty()) {
            for (int i = 0; i < rainIds.size(); i++) {
                QReportRainKeywordEntity qRke = new QReportRainKeywordEntity("rke" + i);
                query.innerJoin(qRke).on(qRke.id.reportId.eq(reportEntity.id).and(qRke.id.rainKeywordId.eq(rainIds.get(i))));
            }
        }

        List<Integer> etceteraIds = keywordFilter.etceteraKeywordIds();
        if (!etceteraIds.isEmpty()) {
            for (int i = 0; i < etceteraIds.size(); i++) {
                QReportEtceteraKeywordEntity qEke = new QReportEtceteraKeywordEntity("eke" + i);
                query.innerJoin(qEke).on(qEke.id.reportId.eq(reportEntity.id).and(qEke.id.etceteraKeywordId.eq(etceteraIds.get(i))));
            }
        }
    }

    private JPAQuery<Long> selectReportIds() {
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

    private List<Report> loadReportsByIds(ReportPageable pageable, List<Long> reportIds, Long userId) {
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
                .leftJoin(reportLikeEntity).on(joinReportLikeCondition(userId))
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
                                reportLikeEntity.id.reportId.isNotNull().as("isLiked"),
                                GroupBy.set(weatherKeywordEntity.keyword),
                                GroupBy.set(rainKeywordEntity.keyword),
                                GroupBy.set(etceteraKeywordEntity.keyword)
                        )
                ));
    }

    private BooleanExpression joinReportLikeCondition(long userId) {
        return reportEntity.id.eq(reportLikeEntity.id.reportId).and(reportLikeEntity.id.userId.eq(userId));
    }
}
