package com.ureca.yoajungadmin.review.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;
import lombok.Getter;

@Getter
public class ReviewNotFoundException extends BusinessException {

    public ReviewNotFoundException(BaseCode baseCode) {
        super(baseCode);
    }
}
