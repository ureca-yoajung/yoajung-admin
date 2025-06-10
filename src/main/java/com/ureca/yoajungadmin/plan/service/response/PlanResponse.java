package com.ureca.yoajungadmin.plan.service.response;

import com.ureca.yoajungadmin.plan.entity.Benefit;
import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.plan.entity.Product;
import com.ureca.yoajungadmin.plan.entity.enums.NetworkType;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanResponse {
    // plan
    private Long id;
    private String name;
    private NetworkType networkType;
    private PlanCategory planCategory;
    private Integer basePrice;
    private Integer dataAllowance;
    private Integer tetheringSharingAllowance;
    private Integer speedAfterLimit;
    private String description;

    private List<ProductResponse> productResponseList;
    private List<BenefitResponse> benefitResponseList;

    public static PlanResponse from(Plan plan, List<Product> products, List<Benefit> benefits) {
        return PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .networkType(plan.getNetworkType())
                .planCategory(plan.getPlanCategory())
                .basePrice(plan.getBasePrice())
                .dataAllowance(plan.getDataAllowance())
                .tetheringSharingAllowance(plan.getTetheringSharingAllowance())
                .speedAfterLimit(plan.getSpeedAfterLimit())
                .description(plan.getDescription())

                .productResponseList(products.stream()
                        .map(ProductResponse::from)
                        .toList())

                .benefitResponseList(benefits.stream()
                        .map(BenefitResponse::from)
                        .toList())
                .build();
    }
}
