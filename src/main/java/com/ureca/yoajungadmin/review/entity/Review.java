package com.ureca.yoajungadmin.review.entity;


import com.ureca.yoajungadmin.common.BaseTimeEntity;
import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planId", nullable = false)
    private Plan plan;

    @Column(name = "star", nullable = false, columnDefinition = "TINYINT")
    private int star;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;


    @Builder
    private Review(User user, Plan plan, int star, String content, boolean isDeleted) {
        this.user = user;
        this.plan = plan;
        this.star = star;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public void updateReview(int star, String content){
        this.star = star;
        this.content = content;
    }

    public void deleteReview(){
        this.isDeleted = true;
    }
}
