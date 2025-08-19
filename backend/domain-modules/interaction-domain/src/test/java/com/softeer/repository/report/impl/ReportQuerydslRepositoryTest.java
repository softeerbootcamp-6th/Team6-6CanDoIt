package com.softeer.repository.report.impl;

import com.softeer.SpringBootTestWithContainer;
import com.softeer.entity.enums.ReportType;
import com.softeer.repository.support.filter.KeywordFilter;
import com.softeer.repository.support.pageable.ReportPageable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTestWithContainer
class ReportQuerydslRepositoryTest {

    @Autowired
    private ReportQuerydslRepository sut;

    private ReportPageable latest(long lastId, int size) {
        return ReportPageable.of(size, lastId);
    }

    @Test
    @DisplayName("코스+타입 조회: LATEST(id DESC) 커서 정상 동작")
    @SqlGroup({
            @Sql(scripts = {"/sql/common_base.sql", "/sql/reports_latest.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)}
    )
    void findReportsByCourseIdAndType_latest() {
        long courseId = 10L;
        int size = 10;

        // 첫 페이지: id DESC → 202, 201
        ReportPageable pageable = latest(Long.MAX_VALUE, size);
        var filter = new KeywordFilter(null, null, null); // 필터 없음
        var page1 = sut.findReportsByCourseIdAndType(pageable, filter, courseId, ReportType.WEATHER);

        assertThat(page1).hasSize(2);
        assertThat(page1.get(0).id()).isEqualTo(202L);
        assertThat(page1.get(1).id()).isEqualTo(201L);

        ReportPageable pageable2 = latest(202L, size);
        var page2 = sut.findReportsByCourseIdAndType(pageable2, filter, courseId, ReportType.WEATHER);
        assertThat(page2.get(0).id()).isEqualTo(201L);

        // 다음 페이지: 커서 id=201 → id < 201 → 없음
        ReportPageable pageable3 = latest(201L, size);
        var page3 = sut.findReportsByCourseIdAndType(pageable3, filter, courseId, ReportType.WEATHER);
        assertThat(page3).isEmpty();
    }

    @Test
    @DisplayName("내 리포트 조회 (LATEST)")
    @SqlGroup({
            @Sql(scripts = {"/sql/common_base.sql", "/sql/reports_my.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)}
    )
    void findMyReports_latest() {
        long userId = 501L;
        int size = 10;

        ReportPageable p1 = latest(Long.MAX_VALUE, size);
        var page1 = sut.findMyReports(p1, userId);
        assertThat(page1).extracting(r -> r.id()).containsExactly(202L, 201L);

        ReportPageable p2 = latest(201L, size);
        var page2 = sut.findMyReports(p2, userId);
        assertThat(page2).isEmpty();
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"/sql/common_base.sql", "/sql/reports_with_keywords.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)}
    )
    void findReportsByCourseIdAndType_withEtcFilter() {
        long courseId = 10L;
        int size = 10;
        var pageable = latest(Long.MAX_VALUE, size);

        // 예시: KeywordFilter(weatherIds=null, rainIds=null, etcIds=List.of(9201))
        var filter = new KeywordFilter(null, null, List.of(9201)); // 구현체 시그에 맞춰 수정
        var results = sut.findReportsByCourseIdAndType(pageable, filter, courseId, ReportType.WEATHER);

        assertThat(results).extracting(r -> r.id()).containsExactly(201L);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = {"/sql/common_base.sql", "/sql/reports_with_keywords.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)}
    )
    void findReportsByCourseIdAndType_withEtcFilter_none_result() {
        long courseId = 10L;
        int size = 10;
        var pageable = latest(Long.MAX_VALUE, size);

        // 예시: KeywordFilter(weatherIds=null, rainIds=null, etcIds=List.of(9201))
        var filter = new KeywordFilter(null, null, List.of(9201)); // 구현체 시그에 맞춰 수정
        var results = sut.findReportsByCourseIdAndType(pageable, filter, courseId, ReportType.SAFE);

        assertThat(results).isEmpty();
    }
}