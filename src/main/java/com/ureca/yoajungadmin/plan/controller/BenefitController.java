package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.controller.request.CreateBenefitRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateBenefitRequest;
import com.ureca.yoajungadmin.plan.entity.enums.BenefitType;
import com.ureca.yoajungadmin.plan.service.BenefitService;
import com.ureca.yoajungadmin.plan.service.response.BenefitResponse;
import com.ureca.yoajungadmin.plan.service.response.ListBenefitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@RestController
@RequestMapping("/benefits")
@RequiredArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;

    @GetMapping("/enums/benefit-types")
    public ResponseEntity<ApiResponse<BenefitType[]>> getBenefitTypeList() {
        return ResponseEntity
                .ok(ApiResponse.of(BENEFIT_TYPE_LIST_SUCCESS, BenefitType.values()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createBenefit(@RequestBody CreateBenefitRequest createBenefitRequest) {
        benefitService.createBenefit(createBenefitRequest);

        return ResponseEntity.status(BENEFIT_CREATE_SUCCESS.getStatus())
                .body(ApiResponse.ok(BENEFIT_CREATE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ListBenefitResponse>> getBenefitList(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(
                ApiResponse.of(BENEFIT_LIST_SUCCESS, benefitService.getBenefitList(pageNumber - 1, pageSize)));
    }

    @GetMapping("/{benefitId}")
    public ResponseEntity<ApiResponse<BenefitResponse>> getBenefit(@PathVariable("benefitId") Long benefitId) {
        return ResponseEntity.ok(
                ApiResponse.of(BENEFIT_DETAIL_SUCCESS, benefitService.getBenefit(benefitId)));
    }

    @PatchMapping("/{benefitId}")
    public ResponseEntity<ApiResponse<?>> editBenefit(@PathVariable("benefitId") Long benefitId,
                                                      @RequestBody UpdateBenefitRequest updateBenefitRequest) {

        benefitService.updateBenefit(benefitId, updateBenefitRequest);

        return ResponseEntity.ok(ApiResponse.ok(BENEFIT_UPDATE_SUCCESS));
    }

    @DeleteMapping("/{benefitId}")
    public ResponseEntity<ApiResponse<?>> removeBenefit(@PathVariable("benefitId") Long benefitId) {
        benefitService.deleteBenefit(benefitId);

        return ResponseEntity.ok(ApiResponse.ok(BENEFIT_DELETE_SUCCESS));
    }
}
