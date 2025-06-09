package com.ureca.yoajungadmin.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getBaseCode().getStatus())
                .body(ApiResponse.of(e.getBaseCode(), null));
    }
}
