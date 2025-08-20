package com.softeer.repository.report.impl.jpa;

import com.softeer.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE ReportEntity r " +
            "SET r.likeCount = r.likeCount + 1 " +
            "WHERE r.id = :reportId")
    void plusLikeCount(long reportId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE ReportEntity r " +
            "SET r.likeCount = r.likeCount - 1 " +
            "WHERE r.id = :reportId")
    void minusLikeCount(long reportId);
}
