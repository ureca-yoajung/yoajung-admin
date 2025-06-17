package com.ureca.yoajungadmin.auth.controller;

import com.ureca.yoajungadmin.auth.dto.SignUpRequest;
import com.ureca.yoajungadmin.auth.service.AuthService;
import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.common.BaseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.of(BaseCode.LOGOUT_SUCCESS, null));
    }

    /**
     * 현재 인증 상태 조회
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Void>> status() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok(ApiResponse.of(BaseCode.STATUS_AUTHENTICATED, null));
        } else {
            return ResponseEntity.ok(ApiResponse.of(BaseCode.STATUS_UNAUTHORIZED, null));
        }
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody SignUpRequest request) {

        authService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok(BaseCode.USER_SIGNUP_SUCCESS));
    }
}
