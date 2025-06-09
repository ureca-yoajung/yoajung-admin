package com.ureca.yoajungadmin.plan.repository;

import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.plan.entity.PlanProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanProductRepository extends JpaRepository<PlanProduct, Long> {
    @EntityGraph(attributePaths = {"product"})
    List<PlanProduct> findByPlanIn(List<Plan> planList);
}
