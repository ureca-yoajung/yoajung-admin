package com.ureca.yoajungadmin.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    @Email(message = "이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
}