package com.ureca.yoajungadmin.summary.scheduler;

import com.ureca.yoajungadmin.plan.repository.PlanRepository;
import com.ureca.yoajungadmin.summary.service.PlanSummaryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlanSummaryScheduler {
    private final PlanRepository planRepository;
    private final PlanSummaryServiceImpl service;

//    @Scheduled(cron = "0 0 3 */3 * ?") // 3일마다 새벽 3시
    public void batchRebuild() {
        planRepository.findAllIds().forEach(id -> {
            try {
                service.rebuildSummary(id);
            } catch (Exception e) {
                log.error("Summary rebuild failed for plan {}", id, e);
            }
        });
    }
}