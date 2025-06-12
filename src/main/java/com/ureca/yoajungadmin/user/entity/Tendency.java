package com.ureca.yoajungadmin.user.entity;


import com.ureca.yoajungadmin.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tendency")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tendency extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "avgMonthlyDataGB", nullable = false)
    private Integer avgMonthlyDataGB;

    @Column(name = "avgMonthlyVoiceMin", nullable = false)
    private Integer avgMonthlyVoiceMin;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "preferencePrice", nullable = false)
    private Integer preferencePrice;

    @Builder
    private Tendency(User user, Integer avgMonthlyDataGB, Integer avgMonthlyVoiceMin, String comment, Integer preferencePrice) {
        this.user = user;
        this.avgMonthlyDataGB = avgMonthlyDataGB;
        this.avgMonthlyVoiceMin = avgMonthlyVoiceMin;
        this.comment = comment;
        this.preferencePrice = preferencePrice;
    }
}
