package com.ureca.yoajungadmin.auth.service;

import com.ureca.yoajungadmin.auth.dto.SignUpRequest;

public interface AuthService {
    void signup(SignUpRequest request);
}