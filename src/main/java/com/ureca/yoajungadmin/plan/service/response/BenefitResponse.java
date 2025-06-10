package com.ureca.yoajungadmin.plan.service.response;

import com.ureca.yoajungadmin.plan.entity.Benefit;
import com.ureca.yoajungadmin.plan.entity.enums.BenefitType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BenefitResponse {
    private Long id;
    private BenefitType benefitType;
    private String name;
    private String description;
    private Integer voiceLimit;
    private Integer smsLimit;
    private Integer discountAmount;

    public static BenefitResponse from(Benefit benefit) {
        return BenefitResponse.builder()
                .id(benefit.getId())
                .benefitType(benefit.getBenefitType())
                .name(benefit.getName())
                .description(benefit.getDescription())
                .voiceLimit(benefit.getVoiceLimit())
                .smsLimit(benefit.getSmsLimit())
                .discountAmount(benefit.getDiscountAmount())
                .build();
    }
}
