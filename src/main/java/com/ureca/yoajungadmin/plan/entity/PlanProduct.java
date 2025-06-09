package com.ureca.yoajungadmin.plan.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
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
