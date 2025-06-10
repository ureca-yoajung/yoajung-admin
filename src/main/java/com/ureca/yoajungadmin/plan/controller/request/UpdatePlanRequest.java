package com.ureca.yoajungadmin.plan.controller.request;

import com.ureca.yoajungadmin.plan.entity.enums.NetworkType;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UpdatePlanRequest {
    private String name;
    private NetworkType networkType;
    private PlanCategory planCategory;
    private Integer basePrice;
    private Integer dataAllowance;
    private Integer tetheringSharingAllowance;
    private Integer speedAfterLimit;
    private String description;
    private List<Long> serviceIds;
    private List<Long> benefitIds;
}
