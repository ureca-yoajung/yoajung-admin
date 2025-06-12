package com.ureca.yoajungadmin.plan.entity;

import com.ureca.yoajungadmin.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="planProduct")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanProduct extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planId", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Builder
    private PlanProduct(Plan plan, Product product) {
        this.plan = plan;
        this.product = product;
    }

    public void changePlan(Plan plan) {
        this.plan = plan;
    }
}
