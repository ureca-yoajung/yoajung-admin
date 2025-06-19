package com.ureca.yoajungadmin.summary.service;

import com.ureca.yoajungadmin.review.entity.Review;
import com.ureca.yoajungadmin.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewSamplingServiceImpl implements ReviewSamplingService{

    private final ReviewRepository reviewRepository;

    public String sampleLatestThirty(Long planId) {
        List<Review> list = reviewRepository.findLatest30ByPlanId(planId);
        return list.stream()
                // 리뷰를 "- (별점) 내용" 으로 표현
                .map(r -> String.format("- (%d★) %s", r.getStar(), r.getContent().replaceAll("\n", " "))) // 줄바꿈 제거
                .collect(Collectors.joining("\n"));
    }
}