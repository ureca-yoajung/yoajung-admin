package com.ureca.yoajungadmin.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BaseCode baseCode;

    public BusinessException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
