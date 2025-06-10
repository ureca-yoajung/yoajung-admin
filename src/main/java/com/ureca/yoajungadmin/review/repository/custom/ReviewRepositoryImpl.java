package com.ureca.yoajungadmin.review.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ureca.yoajungadmin.review.dto.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ureca.yoajungadmin.review.entity.QReview.review;
import static com.ureca.yoajungadmin.review.entity.QReviewLike.reviewLike;
import static com.ureca.yoajungadmin.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 요금제별 리뷰 조회
    @Override
    public Page<ReviewListResponse> findReviewList(Long planId, Pageable pageable) {
        // 조회 (페이징)
        List<ReviewListResponse> reviewList = jpaQueryFactory
                .select(Projections.constructor(ReviewListResponse.class,
                        review.id,
                        review.user.id,
                        review.user.name,
                        review.content,
                        review.star,
                        reviewLike.id.count(),
                        review.createDate
                ))
                .from(review)
                .join(review.user, user)
                .leftJoin(reviewLike).on(reviewLike.review.id.eq(review.id))
                .where(review.plan.id.eq(planId)
                        .and(review.isDeleted.isFalse()))
                .groupBy(review.id, review.user.id, review.user.name, review.content, review.star, review.createDate)
                .orderBy(review.createDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        // 개수 count
        JPAQuery<Long> count = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(review.plan.id.eq(planId)
                        .and(review.isDeleted.isFalse()));

        return PageableExecutionUtils.getPage(reviewList, pageable, count::fetchOne);
    }

    // 전체 리뷰 조회
    @Override
    public Page<ReviewListResponse> findReviewAllList(Pageable pageable) {
        // 조회 (페이징)
        List<ReviewListResponse> reviewList = jpaQueryFactory
                .select(Projections.constructor(ReviewListResponse.class,
                        review.id,
                        review.user.id,
                        review.user.name,
                        review.content,
                        review.star,
                        reviewLike.id.count(),
                        review.createDate
                ))
                .from(review)
                .join(review.user, user)
                .leftJoin(reviewLike).on(reviewLike.review.id.eq(review.id))
                .where(review.isDeleted.isFalse())
                .groupBy(review.id, review.user.id, review.user.name, review.content, review.star, review.createDate)
                .orderBy(review.createDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        // 개수 count
        JPAQuery<Long> count = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(review.isDeleted.isFalse());

        return PageableExecutionUtils.getPage(reviewList, pageable, count::fetchOne);
    }

    // 별점 평균
    @Override
    public Double avgStar(Long planId) {
        return jpaQueryFactory
                .select(review.star.avg())
                .from(review)
                .where(review.plan.id.eq(planId)
                        .and(review.isDeleted.isFalse()))
                .fetchOne();
    }

}