package com.ureca.yoajungadmin.review.entity;


import com.ureca.yoajungadmin.common.BaseTimeEntity;
import com.ureca.yoajungadmin.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="review_like",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","review_id"})}) // unique 제약조건 설정.
public class ReviewLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id", nullable = false)
    private Review review;


    @Builder
    private ReviewLike(Long id, User user, Review review) {
        this.id = id;
        this.user = user;
        this.review = review;
    }
}
