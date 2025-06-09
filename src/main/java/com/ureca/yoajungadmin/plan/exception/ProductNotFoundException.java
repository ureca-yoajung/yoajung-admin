package com.ureca.yoajungadmin.plan.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;
import lombok.Getter;


@Getter
public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(BaseCode baseCode) {
        super(baseCode);
    }
}
