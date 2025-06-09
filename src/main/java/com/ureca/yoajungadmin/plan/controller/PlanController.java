package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdatePlanRequest;
import com.ureca.yoajungadmin.plan.service.PlanService;
import com.ureca.yoajungadmin.plan.service.response.ListPlanResponse;
import com.ureca.yoajungadmin.plan.service.response.PlanResponse;
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

    @DeleteMapping("/{planId}")
    public ResponseEntity<ApiResponse<?>> removePlan(@PathVariable("planId") Long planId) {
        planService.deletePlan(planId);

        return ResponseEntity.ok(ApiResponse.ok(PLAN_DELETE_SUCCESS));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<PlanResponse>> getPlan(@PathVariable("planId") Long planId) {
        return ResponseEntity.ok(ApiResponse.of(PLAN_DELETE_SUCCESS, planService.getPlan(planId)));
    }

    @PutMapping("/{planId}")
    public ResponseEntity<ApiResponse<?>> updatePlan(@PathVariable("planId") Long planId,
                                                                @RequestBody UpdatePlanRequest updatePlanRequest) {
        planService.updatePlan(planId, updatePlanRequest);

        return ResponseEntity.ok(
                ApiResponse.ok(PLAN_UPDATE_SUCCESS));
    }
}
