package com.ureca.yoajungadmin.plan.controller.request;

import com.ureca.yoajungadmin.plan.entity.enums.BenefitType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateBenefitRequest {
    private BenefitType benefitType;
    private String name;
    private String description;
    private Integer voiceLimit;
    private Integer smsLimit;
    private Integer discountAmount;
}
