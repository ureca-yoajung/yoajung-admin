package com.ureca.yoajungadmin.plan.entity;


import com.ureca.yoajungadmin.common.BaseTimeEntity;
import com.ureca.yoajungadmin.plan.entity.enums.NetworkType;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Plan SET deletedAt = NOW() WHERE id = ?")
@SQLRestriction("deletedAt is NULL")
public class Plan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NetworkType networkType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanCategory planCategory;

    @Column(nullable = false)
    private Integer basePrice;

    @Column(nullable = false)
    private Integer dataAllowance;

    @Column(nullable = false)
    private Integer tetheringSharingAllowance;

    @Column(nullable = false)
    private Integer speedAfterLimit;

    @Column(nullable = false)
    private String description;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanBenefit> planBenefits = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanProduct> planProducts = new ArrayList<>();

    public void addBenefit(PlanBenefit planBenefit) {
        planBenefits.add(planBenefit);
        planBenefit.changePlan(this);
    }

    public void removeBenefit(PlanBenefit planBenefit) {
        planBenefits.remove(planBenefit);
        planBenefit.changePlan(null);
    }

    public void addProduct(PlanProduct planProduct) {
        planProducts.add(planProduct);
        planProduct.changePlan(this);
    }

    public void removeProduct(PlanProduct planProduct) {
        planProducts.remove(planProduct);
        planProduct.changePlan(null);
    }

    @Builder
    private Plan(String name, NetworkType networkType, PlanCategory planCategory,
                 Integer basePrice, Integer dataAllowance,
                 Integer tetheringSharingAllowance, Integer speedAfterLimit, String description,
                 List<PlanBenefit> planBenefits, List<PlanProduct> planProducts) {
        this.name = name;
        this.networkType = networkType;
        this.planCategory = planCategory;
        this.basePrice = basePrice;
        this.dataAllowance = dataAllowance;
        this.tetheringSharingAllowance = tetheringSharingAllowance;
        this.speedAfterLimit = speedAfterLimit;
        this.description = description;

        // 연관관계 세팅: null 체크 후 추가
        if (planBenefits != null) {
            planBenefits.forEach(this::addBenefit);
        }
        if (planProducts != null) {
            planProducts.forEach(this::addProduct);
        }
    }

    public void update(String name, NetworkType networkType,
                       PlanCategory planCategory, Integer basePrice,
                       Integer dataAllowance, Integer tetheringSharingAllowance,
                       Integer speedAfterLimit, String description) {
        this.name = name;
        this.networkType = networkType;
        this.planCategory = planCategory;
        this.basePrice = basePrice;
        this.dataAllowance = dataAllowance;
        this.tetheringSharingAllowance = tetheringSharingAllowance;
        this.speedAfterLimit = speedAfterLimit;
        this.description = description;
    }
}
