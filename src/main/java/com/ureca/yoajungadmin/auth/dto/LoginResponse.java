package com.ureca.yoajungadmin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String email;
    private String name;
    private String role;
}