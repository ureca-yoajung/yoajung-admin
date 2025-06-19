package com.ureca.yoajungadmin.summary.service;

import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.plan.repository.PlanRepository;
import com.ureca.yoajungadmin.summary.client.DifyClient;
import com.ureca.yoajungadmin.summary.entity.PlanSummary;
import com.ureca.yoajungadmin.summary.repository.PlanSummaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanSummaryServiceImpl implements PlanSummaryService{

    private final PlanRepository planRepository;
    private final ReviewSamplingServiceImpl samplingService;
    private final DifyClient difyClient;
    private final PlanSummaryRepository summaryRepository;

    @Transactional
    public void rebuildSummary(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        String planInfo = String.format("이름:%s, 가격:%d원, 데이터:%dGB", plan.getName(), plan.getBasePrice(), plan.getDataAllowance());
        String reviews = samplingService.sampleLatestThirty(planId);
        String summary = difyClient.requestSummary(planInfo, reviews);
        if (summary == null || summary.isBlank()) {
            throw new IllegalStateException("Dify 요약 생성 실패");
        }

        PlanSummary entity = PlanSummary.builder()
                .planId(planId)
                .summaryText(summary) // dify 결과
                .reviewCountSnapshot(reviews.split("\n").length) // 사용된 리뷰 줄 수
                .build();
        summaryRepository.save(entity);
    }
}