package com.ureca.yoajungadmin.plan.service;

import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdatePlanRequest;
import com.ureca.yoajungadmin.plan.service.response.ListPlanResponse;
import com.ureca.yoajungadmin.plan.service.response.PlanResponse;

public interface PlanService {
    Long createPlan(CreatePlanRequest createPlanRequest);
    ListPlanResponse getPlanList(Integer pageNumber, Integer pageSize);
    PlanResponse getPlan(Long planId);
    void deletePlan(Long planId);
    void updatePlan(Long planId, UpdatePlanRequest updatePlanRequest);
}
