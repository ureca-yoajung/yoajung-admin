package com.ureca.yoajungadmin.plan.entity;

import com.ureca.yoajungadmin.common.BaseTimeEntity;
import com.ureca.yoajungadmin.plan.entity.enums.BenefitType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "benefit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Benefit extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="benefitType", nullable = false)
    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name="voiceLimit")
    private Integer voiceLimit;

    @Column(name="smsLimit")
    private Integer smsLimit;

    @Column(name="discountAmount")
    private Integer discountAmount;

    @Builder
    private Benefit(BenefitType benefitType, String name, String description, Integer voiceLimit, Integer smsLimit, Integer discountAmount) {
        this.benefitType = benefitType;
        this.name = name;
        this.description = description;
        this.voiceLimit = voiceLimit;
        this.smsLimit = smsLimit;
        this.discountAmount = discountAmount;
    }

    public void update(BenefitType benefitType, String name, String description, Integer voiceLimit, Integer smsLimit, Integer discountAmount) {
        this.benefitType = benefitType;
        this.name = name;
        this.description = description;
        this.voiceLimit = voiceLimit;
        this.smsLimit = smsLimit;
        this.discountAmount = discountAmount;
    }
}
