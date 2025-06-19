package com.ureca.yoajungadmin.summary.service;

import com.ureca.yoajungadmin.summary.dto.PlanSummaryDto;

public interface PlanSummaryService {
    void rebuildSummary(Long planId);

    PlanSummaryDto getSummary(Long planId);
}
