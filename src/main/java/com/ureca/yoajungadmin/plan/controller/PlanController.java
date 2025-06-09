package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPlan(@RequestBody CreatePlanRequest createPlanRequest) {
        planService.createPlan(createPlanRequest);

        return ResponseEntity.ok(ApiResponse.ok(BaseCode.PLAN_CREATE_SUCCESS));
    }
}
