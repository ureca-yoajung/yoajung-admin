package com.ureca.yoajungadmin.review.repository;

import com.ureca.yoajungadmin.review.entity.Review;
import com.ureca.yoajungadmin.review.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    // 최신순 30개 리뷰 조회 (삭제되지 않은 리뷰만)
    @Query(value = """
            SELECT
              id,
              isDeleted,
              star,
              createDate,
              lastModifiedDate,
              planId    AS planId,   -- alias for field mapping
              userId    AS userId,   -- alias for field mapping
              content
            FROM review
            WHERE plan_id = :planId
              AND isDeleted = false
            ORDER BY createDate DESC
            LIMIT 30
            """, nativeQuery = true)
    List<Review> findLatest30ByPlanId(@Param("planId") Long planId);
}
