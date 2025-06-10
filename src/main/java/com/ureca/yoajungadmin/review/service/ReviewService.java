package com.ureca.yoajungadmin.review.service;

import com.ureca.yoajungadmin.review.dto.ReviewAllPageResponse;
import com.ureca.yoajungadmin.review.dto.ReviewDeleteResponse;
import com.ureca.yoajungadmin.review.dto.ReviewPageResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewPageResponse reviewList(Long planId, Pageable pageable);
    ReviewAllPageResponse reviewAllList(Pageable pageable);
    ReviewDeleteResponse deleteReview(Long reviewId);
}
