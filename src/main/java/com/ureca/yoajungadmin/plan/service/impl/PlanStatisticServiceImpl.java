package com.ureca.yoajungadmin.plan.service.impl;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.plan.entity.PlanStatistic;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.plan.exception.PlanStatisticNotFoundException;
import com.ureca.yoajungadmin.plan.repository.PlanStatisticRepository;
import com.ureca.yoajungadmin.plan.service.PlanStatisticService;
import com.ureca.yoajungadmin.plan.service.response.PlanStatisticResponse;
import com.ureca.yoajungadmin.user.entity.AgeGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanStatisticServiceImpl implements PlanStatisticService {

    private final PlanStatisticRepository planStatisticRepository;

    @Override
    public List<PlanStatisticResponse> getPlanStatistic() {
        PlanStatistic latestStatistic = planStatisticRepository
                .findTopByOrderByCreateDateDesc()
                .orElseThrow(() -> new PlanStatisticNotFoundException(BaseCode.PLAN_STATISTICS_NOT_FOUND));

        LocalDateTime startOfDay = latestStatistic.getCreateDate().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<PlanStatisticResponse> responses = new ArrayList<>();

        for (AgeGroup ageGroup : AgeGroup.values()) {
            if (ageGroup != AgeGroup.ALL) {
                responses.add(createStatisticResponseForAgeGroup(ageGroup, startOfDay, endOfDay));
            }
        }

        return responses;
    }

    private PlanStatisticResponse createStatisticResponseForAgeGroup(AgeGroup ageGroup, LocalDateTime start, LocalDateTime end) {
        List<PlanStatistic> stats = planStatisticRepository
                .findAllByAgeGroupAndPlanCategoryAndCreateDateBetween(ageGroup, PlanCategory.ALL, start, end);

        long totalUserCount = stats.stream()
                .mapToLong(PlanStatistic::getUserCount)
                .sum();

        return new PlanStatisticResponse(ageGroup, totalUserCount);
    }
}
