package com.ureca.yoajungadmin.summary.scheduler;

import com.ureca.yoajungadmin.plan.repository.PlanRepository;
import com.ureca.yoajungadmin.summary.service.PlanSummaryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlanSummaryScheduler {
    private final PlanRepository planRepository;
    private final PlanSummaryServiceImpl service;

//    @Scheduled(cron = "0 0 3 */3 * ?") // 3일마다 새벽 3시
    public void batchRebuild() {
        long start = System.currentTimeMillis();

        planRepository.findAllIds().forEach(id -> {
            try {
                service.rebuildSummary(id);
            } catch (Exception e) {
                log.error("Summary rebuild failed for plan {}", id, e);
            }
        });

        long end = System.currentTimeMillis();
        log.info("전체 요약 처리 시간: {}초", (end - start) / 1000.0);

    }

// 선택한 요금제만 배치
    public void batchRebuild2() {
        long start = System.currentTimeMillis();

        // 리뷰가 있는 플랜 ID만 지정
        List<Long> planIdsWithReviews = List.of(1L, 3L, 4L, 6L, 7L, 18L, 19L, 22L, 23L, 25L, 32L, 33L, 34L);

        planIdsWithReviews.forEach(id -> {
            try {
                service.rebuildSummary(id);
            } catch (Exception e) {
                log.error("Summary rebuild failed for plan {}", id, e);
            }
        });

        long end = System.currentTimeMillis();
        log.info("선택된 플랜 요약 처리 시간: {}초", (end - start) / 1000.0);
    }
}