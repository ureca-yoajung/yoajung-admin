package com.ureca.yoajungadmin.plan.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;
import lombok.Getter;

@Getter
public class PlanStatisticNotFoundException extends BusinessException {
    public PlanStatisticNotFoundException(BaseCode baseCode) {
        super(baseCode);
    }
}
