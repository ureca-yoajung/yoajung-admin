package com.ureca.yoajungadmin.review.repository.custom;


import com.ureca.yoajungadmin.review.dto.ReviewListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewListResponse> findReviewList(Long planId, Pageable pageable);
    Page<ReviewListResponse> findReviewAllList(Pageable pageable);
    Double avgStar(Long planId);
}
