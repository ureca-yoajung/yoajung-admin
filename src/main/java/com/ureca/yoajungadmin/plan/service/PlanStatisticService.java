package com.ureca.yoajungadmin.plan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ureca.yoajungadmin.plan.service.response.PlanStatisticInfoResponse;

public interface PlanStatisticService {

    PlanStatisticInfoResponse getPlanStatistic() throws JsonProcessingException;
}
