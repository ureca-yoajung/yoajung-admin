package com.ureca.yoajungadmin.plan.repository.custom;

import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.user.entity.AgeGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PopularPlanDto {
    private Long planId;
    private Long userCount;
    private AgeGroup ageGroup;
    private PlanCategory planCategory;
}
