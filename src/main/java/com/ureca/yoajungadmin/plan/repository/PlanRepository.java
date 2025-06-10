package com.ureca.yoajungadmin.plan.repository;

import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.plan.repository.custom.PlanRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {
}
