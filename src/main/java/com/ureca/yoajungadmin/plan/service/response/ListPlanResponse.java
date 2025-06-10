package com.ureca.yoajungadmin.plan.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ListPlanResponse {
    List<PlanResponse> planResponseList;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
}
