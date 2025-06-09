package com.ureca.yoajungadmin.plan.service.impl;

import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.entity.*;
import com.ureca.yoajungadmin.plan.exception.BenefitNotFoundException;
import com.ureca.yoajungadmin.plan.exception.ProductNotFoundException;
import com.ureca.yoajungadmin.plan.repository.BenefitRepository;
import com.ureca.yoajungadmin.plan.repository.PlanRepository;
import com.ureca.yoajungadmin.plan.repository.ProductRepository;
import com.ureca.yoajungadmin.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final BenefitRepository benefitRepository;
    private final ProductRepository productRepository;
    private final PlanRepository planRepository;

    @Override
    public Long createPlan(CreatePlanRequest createPlanRequest) {
        Plan plan = Plan.builder()
                .name(createPlanRequest.getName())
                .networkType(createPlanRequest.getNetworkType())
                .planCategory(createPlanRequest.getPlanCategory())
                .basePrice(createPlanRequest.getBasePrice())
                .dataAllowance(createPlanRequest.getDataAllowance())
                .tetheringSharingAllowance(createPlanRequest.getTetheringSharingAllowance())
                .speedAfterLimit(createPlanRequest.getSpeedAfterLimit())
                .description(createPlanRequest.getDescription())
                .build();


        for (Long benefitId : createPlanRequest.getBenefitIdList()) {
            Benefit benefit = benefitRepository.findById(benefitId).orElseThrow(() -> new BenefitNotFoundException(BENEFIT_NOT_FOUND));

            plan.addBenefit(PlanBenefit.builder()
                    .benefit(benefit)
                    .build());
        }

        for (Long productId : createPlanRequest.getProductIdList()) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

            plan.addProduct(PlanProduct.builder()
                    .product(product)
                    .build());
        }


        planRepository.save(plan);

        return plan.getId();
    }
}
