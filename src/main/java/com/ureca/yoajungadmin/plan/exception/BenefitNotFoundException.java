package com.ureca.yoajungadmin.plan.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;
import lombok.Getter;

@Getter
public class BenefitNotFoundException extends BusinessException {

    public BenefitNotFoundException(BaseCode baseCode) {
        super(baseCode);
    }
}
