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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private int star;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
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
