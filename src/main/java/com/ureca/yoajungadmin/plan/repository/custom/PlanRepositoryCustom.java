package com.ureca.yoajungadmin.plan.repository.custom;

import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.user.entity.AgeGroup;

import java.util.List;

public interface PlanRepositoryCustom {
    List<PopularPlanDto> findOverallPopularPlans(AgeGroup ageGroup, PlanCategory category);
}
