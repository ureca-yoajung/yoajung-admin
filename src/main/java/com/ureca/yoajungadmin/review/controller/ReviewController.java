package com.ureca.yoajungadmin.review.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.review.dto.ReviewAllPageResponse;
import com.ureca.yoajungadmin.review.dto.ReviewDeleteResponse;
import com.ureca.yoajungadmin.review.dto.ReviewPageResponse;
import com.ureca.yoajungadmin.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ureca.yoajungadmin.common.BaseCode.REVIEW_DELETE_SUCCESS;
import static com.ureca.yoajungadmin.common.BaseCode.STATUS_OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    // 요금제별 리뷰 조회
    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<ReviewPageResponse>> listReview(@PathVariable Long planId, Pageable pageable) {

        ReviewPageResponse response = reviewService.reviewList(planId, pageable);

        return ResponseEntity.status(STATUS_OK.getStatus())
                .body(ApiResponse.of(STATUS_OK, response));
    }

    // 전체 리뷰 조회
    @GetMapping
    public ResponseEntity<ApiResponse<ReviewAllPageResponse>> listReview(Pageable pageable) {

        ReviewAllPageResponse response = reviewService.reviewAllList(pageable);

        return ResponseEntity.status(STATUS_OK.getStatus())
                .body(ApiResponse.of(STATUS_OK, response));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewDeleteResponse>> deleteReview(@PathVariable Long reviewId) {
        ReviewDeleteResponse response = reviewService.deleteReview(reviewId);

        return ResponseEntity.status(REVIEW_DELETE_SUCCESS.getStatus())
                .body(ApiResponse.of(REVIEW_DELETE_SUCCESS, response));
    }
}
