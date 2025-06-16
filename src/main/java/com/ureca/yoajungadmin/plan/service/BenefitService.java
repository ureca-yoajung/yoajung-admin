package com.ureca.yoajungadmin.plan.service;

import com.ureca.yoajungadmin.plan.controller.request.CreateBenefitRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateBenefitRequest;
import com.ureca.yoajungadmin.plan.service.response.BenefitResponse;
import com.ureca.yoajungadmin.plan.service.response.ListBenefitResponse;

public interface BenefitService {
    Long createBenefit(CreateBenefitRequest createBenefitRequest);
    BenefitResponse getBenefit(Long benefitId);
    ListBenefitResponse getBenefitList(Integer pageNumber, Integer pageSize);
    ListBenefitResponse getAllBenefitList();
    void updateBenefit(Long benefitId, UpdateBenefitRequest updateBenefitRequest);
    void deleteBenefit(Long benefitId);
}
