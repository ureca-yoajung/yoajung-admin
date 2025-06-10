package com.ureca.yoajungadmin.plan.service;

import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;

public interface PlanService {
    Long createPlan(CreatePlanRequest createPlanRequest);
}
