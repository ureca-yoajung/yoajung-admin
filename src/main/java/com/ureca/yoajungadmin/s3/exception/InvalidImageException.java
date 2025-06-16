package com.ureca.yoajungadmin.s3.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;
import lombok.Getter;

@Getter
public class InvalidImageException extends BusinessException {

    public InvalidImageException(BaseCode baseCode) {
        super(baseCode);
    }
}
