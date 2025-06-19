package com.ureca.yoajungadmin.summary.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.summary.dto.PlanSummaryDto;
import com.ureca.yoajungadmin.summary.service.PlanSummaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.ureca.yoajungadmin.common.BaseCode;

@RestController
@RequestMapping("/admin/plans/{planId}/summary")
@RequiredArgsConstructor
public class PlanSummaryAdminController {

    private final PlanSummaryServiceImpl planSummaryService;

    @PostMapping("/rebuild")
    public ApiResponse<Void> rebuild(@PathVariable("planId") Long planId) {
        planSummaryService.rebuildSummary(planId);
        return ApiResponse.ok(BaseCode.DIFY_SUMMARY_SUCCESS);
    }

    @GetMapping
    public ApiResponse<PlanSummaryDto> getSummary(@PathVariable("planId") Long planId) {
        PlanSummaryDto dto = planSummaryService.getSummary(planId);
        if (dto == null) {
            return ApiResponse.ok(BaseCode.PLAN_SUMMARY_NOT_FOUND);
        }
        return ApiResponse.of(BaseCode.PLAN_SUMMARY_SUCCESS, dto);
    }
}

