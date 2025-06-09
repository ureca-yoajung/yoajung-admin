package com.ureca.yoajungadmin.plan.service;

import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.service.response.ListPlanResponse;

public interface PlanService {
    Long createPlan(CreatePlanRequest createPlanRequest);
    ListPlanResponse getPlanList(Integer pageNumber, Integer pageSize);
}
