package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.service.PlanStatisticService;
import com.ureca.yoajungadmin.plan.service.response.PlanStatisticResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ureca.yoajungadmin.common.BaseCode.PLAN_STATISTICS_READ_SUCCESS;

@RestController
@RequestMapping("/plan-statistics")
@RequiredArgsConstructor
public class PlanStatisticController {

    private final PlanStatisticService planStatisticService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlanStatisticResponse>>> getPlanStatistic() {
        return ResponseEntity.ok(ApiResponse
                .of(PLAN_STATISTICS_READ_SUCCESS, planStatisticService.getPlanStatistic()));
    }
}
