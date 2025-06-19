package com.ureca.yoajungadmin.plan.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class PlanStatisticInfoResponse {
    private List<PlanStatisticResponse> planStatisticResponses;
    private String content;
}
