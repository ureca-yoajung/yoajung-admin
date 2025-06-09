package com.ureca.yoajungadmin.plan.repository;

import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.plan.entity.PlanBenefit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanBenefitRepository extends JpaRepository<PlanBenefit, Long> {
    @EntityGraph(attributePaths = {"benefit"})
    List<PlanBenefit> findByPlanIn(List<Plan> plans);
}
