package com.ureca.yoajungadmin.plan.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListBenefitResponse {
    private List<BenefitResponse> benefitResponseList;
}
