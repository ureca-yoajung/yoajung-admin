package com.ureca.yoajungadmin.summary.controller;

import com.ureca.yoajungadmin.summary.scheduler.PlanSummaryScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final PlanSummaryScheduler scheduler;

    @PostMapping("/test/summary-batch")
    public String testBatchRun() {
        scheduler.batchRebuild();
        return "요약 배치 실행 완료";
    }
}