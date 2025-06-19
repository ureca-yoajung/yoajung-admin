package com.ureca.yoajungadmin.plan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.service.PlanStatisticService;
import com.ureca.yoajungadmin.plan.service.response.PlanStatisticInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.ureca.yoajungadmin.common.BaseCode.PLAN_STATISTICS_READ_SUCCESS;

@RestController
@RequestMapping("/plan-statistics")
@RequiredArgsConstructor
public class PlanStatisticController {

    private final PlanStatisticService planStatisticService;

    @GetMapping
    public ResponseEntity<ApiResponse<PlanStatisticInfoResponse>> getPlanStatistic() throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse
                .of(PLAN_STATISTICS_READ_SUCCESS, planStatisticService.getPlanStatistic()));
    }
}
