package com.ureca.yoajungadmin.s3.exception;

import com.ureca.yoajungadmin.common.BaseCode;
import com.ureca.yoajungadmin.common.BusinessException;

public class ImageUploadFailException extends BusinessException {

    public ImageUploadFailException() {
        super(BaseCode.IMAGE_UPLOAD_FAIL);
    }
}

