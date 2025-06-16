package com.ureca.yoajungadmin.plan.service.impl;

import com.ureca.yoajungadmin.plan.controller.request.CreateBenefitRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateBenefitRequest;
import com.ureca.yoajungadmin.plan.entity.Benefit;
import com.ureca.yoajungadmin.plan.exception.BenefitNotFoundException;
import com.ureca.yoajungadmin.plan.repository.BenefitRepository;
import com.ureca.yoajungadmin.plan.service.BenefitService;
import com.ureca.yoajungadmin.plan.service.response.BenefitResponse;
import com.ureca.yoajungadmin.plan.service.response.ListBenefitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;

    @Override
    public Long createBenefit(CreateBenefitRequest createBenefitRequest) {
        Benefit benefit = Benefit.builder()
                .benefitType(createBenefitRequest.getBenefitType())
                .name(createBenefitRequest.getName())
                .description(createBenefitRequest.getDescription())
                .voiceLimit(createBenefitRequest.getVoiceLimit())
                .smsLimit(createBenefitRequest.getSmsLimit())
                .discountAmount(createBenefitRequest.getDiscountAmount())
                .build();

        benefitRepository.save(benefit);

        return benefit.getId();
    }

    @Override
    public BenefitResponse getBenefit(Long benefitId) {
        Benefit benefit = benefitRepository
                .findById(benefitId)
                .orElseThrow(() -> new BenefitNotFoundException(BENEFIT_NOT_FOUND));

        return BenefitResponse.from(benefit);
    }

    @Override
    public ListBenefitResponse getBenefitList(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());

        Page<Benefit> benefitPage = benefitRepository.findAll(pageRequest);

        List<BenefitResponse> benefitResponseList = benefitPage.getContent().stream().map(BenefitResponse::from)
                .toList();

        return new ListBenefitResponse(benefitResponseList, benefitPage.getNumber() + 1, benefitPage.getSize(), benefitPage.getTotalElements());
    }

    @Override
    public ListBenefitResponse getAllBenefitList() {
        List<BenefitResponse> benefitResponseList = benefitRepository.findAll()
                .stream()
                .map(BenefitResponse::from)
                .toList();

        return new ListBenefitResponse(benefitResponseList, 0, 0, 0L);
    }

    @Override
    public void updateBenefit(Long benefitId, UpdateBenefitRequest updateBenefitRequest) {
        Benefit benefit = benefitRepository
                .findById(benefitId)
                .orElseThrow(() -> new BenefitNotFoundException(BENEFIT_NOT_FOUND));

        benefit.update(
                updateBenefitRequest.getBenefitType(),
                updateBenefitRequest.getName(),
                updateBenefitRequest.getDescription(),
                updateBenefitRequest.getVoiceLimit(),
                updateBenefitRequest.getSmsLimit(),
                updateBenefitRequest.getDiscountAmount()
        );
    }

    @Override
    public void deleteBenefit(Long benefitId) {
        benefitRepository.deleteById(benefitId);
    }
}
