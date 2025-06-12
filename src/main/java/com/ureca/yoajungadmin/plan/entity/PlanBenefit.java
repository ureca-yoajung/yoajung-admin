package com.ureca.yoajungadmin.plan.entity;

import com.ureca.yoajungadmin.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "planBenefit")
@NoArgsConstructor
public class PlanBenefit extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planId", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefitId", nullable = false)
    private Benefit benefit;

    public void changePlan(Plan plan) {
        this.plan = plan;
    }

    @Builder
    private PlanBenefit(Plan plan, Benefit benefit) {
        this.plan = plan;
        this.benefit = benefit;
    }
}
