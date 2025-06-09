package com.ureca.yoajungadmin.plan.service.impl;

import com.ureca.yoajungadmin.plan.controller.request.CreatePlanRequest;
import com.ureca.yoajungadmin.plan.entity.*;
import com.ureca.yoajungadmin.plan.exception.BenefitNotFoundException;
import com.ureca.yoajungadmin.plan.exception.ProductNotFoundException;
import com.ureca.yoajungadmin.plan.repository.*;
import com.ureca.yoajungadmin.plan.service.PlanService;
import com.ureca.yoajungadmin.plan.service.response.BenefitResponse;
import com.ureca.yoajungadmin.plan.service.response.ListPlanResponse;
import com.ureca.yoajungadmin.plan.service.response.PlanResponse;
import com.ureca.yoajungadmin.plan.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ureca.yoajungadmin.common.BaseCode.BENEFIT_NOT_FOUND;
import static com.ureca.yoajungadmin.common.BaseCode.PRODUCT_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final BenefitRepository benefitRepository;
    private final ProductRepository productRepository;
    private final PlanRepository planRepository;
    private final PlanProductRepository planProductRepository;
    private final PlanBenefitRepository planBenefitRepository;

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

    @Override
    public ListPlanResponse getPlanList(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("lastModifiedDate").descending());

        Page<Plan> planPage = planRepository.findAll(pageRequest);
        List<Plan> plans = planPage.getContent();

        List<PlanProduct> planProducts = planProductRepository.findByPlanIn(plans);
        List<PlanBenefit> planBenefits = planBenefitRepository.findByPlanIn(plans);

        Map<Plan, List<ProductResponse>> productMap = planProducts.stream()
                .collect(Collectors.groupingBy(
                        PlanProduct::getPlan,
                        Collectors.mapping(pp -> ProductResponse.from(pp.getProduct()), Collectors.toList())
                ));

        Map<Plan, List<BenefitResponse>> benefitMap = planBenefits.stream()
                .collect(Collectors.groupingBy(
                        PlanBenefit::getPlan,
                        Collectors.mapping(pb -> BenefitResponse.from(pb.getBenefit()), Collectors.toList())
                ));

        List<PlanResponse> planResponseList = plans.stream()
                .map(plan -> PlanResponse.builder()
                        .id(plan.getId())
                        .name(plan.getName())
                        .networkType(plan.getNetworkType())
                        .planCategory(plan.getPlanCategory())
                        .basePrice(plan.getBasePrice())
                        .dataAllowance(plan.getDataAllowance())
                        .tetheringSharingAllowance(plan.getTetheringSharingAllowance())
                        .speedAfterLimit(plan.getSpeedAfterLimit())
                        .description(plan.getDescription())
                        .productResponseList(productMap.getOrDefault(plan, List.of()))
                        .benefitResponseList(benefitMap.getOrDefault(plan, List.of()))
                        .build()
                )
                .toList();

        return ListPlanResponse.builder()
                .planResponseList(planResponseList)
                .pageNumber(planPage.getNumber())
                .pageSize(planPage.getSize())
                .totalElements(planPage.getTotalElements())
                .build();
    }

    @Override
    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }
}
