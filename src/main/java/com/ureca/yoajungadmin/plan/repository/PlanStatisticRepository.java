package com.ureca.yoajungadmin.plan.repository;

import com.ureca.yoajungadmin.plan.entity.PlanStatistic;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.user.entity.AgeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlanStatisticRepository extends JpaRepository<PlanStatistic, Long> {
    Optional<PlanStatistic> findTopByOrderByCreateDateDesc();
    List<PlanStatistic> findAllByAgeGroupAndPlanCategoryAndCreateDateBetween(AgeGroup ageGroup, PlanCategory planCategory, LocalDateTime start, LocalDateTime end);
}
