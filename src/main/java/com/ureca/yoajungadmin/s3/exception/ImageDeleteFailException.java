package com.ureca.yoajungadmin.s3.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;

public class ImageDeleteFailException extends BusinessException {

    public ImageDeleteFailException() {
        super(BaseCode.IMAGE_DELETE_FAIL);
    }
}

