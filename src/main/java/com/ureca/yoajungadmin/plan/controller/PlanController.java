package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.service.PlanService;
import com.ureca.yoajungadmin.plan.service.response.ListPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPlan(@RequestBody CreatePlanRequest createPlanRequest) {
        planService.createPlan(createPlanRequest);

        return ResponseEntity.ok(ApiResponse.ok(PLAN_CREATE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getPlanList(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        ListPlanResponse listPlanResponse = planService.getPlanList(pageNumber - 1, pageSize);

        return ResponseEntity.ok(ApiResponse.of(PLAN_LIST_SUCCESS, listPlanResponse));
    }
}
