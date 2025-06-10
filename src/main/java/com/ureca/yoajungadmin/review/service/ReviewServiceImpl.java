package com.ureca.yoajungadmin.review.service;

import com.ureca.yoajungadmin.review.dto.*;
import com.ureca.yoajungadmin.review.entity.Review;
import com.ureca.yoajungadmin.review.exception.ReviewNotFoundException;
import com.ureca.yoajungadmin.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.ureca.yoajungadmin.common.BaseCode.REVIEW_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    // 요금제별 리뷰 조회
    @Override
    public ReviewPageResponse reviewList(Long planId, Pageable pageable) {
        Page<ReviewListResponse> reviewList = reviewRepository.findReviewList(planId, pageable);
        Double avgStar = reviewRepository.avgStar(planId);

        if(avgStar == null){
            avgStar = 0.0;
        }

        return new ReviewPageResponse(reviewList, avgStar);
    }

    // 전체 리뷰 조회
    @Override
    public ReviewAllPageResponse reviewAllList(Pageable pageable) {

        Page<ReviewListResponse> reviewAllList = reviewRepository.findReviewAllList(pageable);

        return new ReviewAllPageResponse(reviewAllList);
    }

    // 리뷰 삭제
    @Override
    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ReviewNotFoundException(REVIEW_NOT_FOUND));

        review.deleteReview();

        return ReviewDeleteResponse.builder()
                .reviewId(review.getId())
                .build();
    }
}
