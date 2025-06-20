package com.ureca.yoajungadmin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              // 로그인 페이지 & 정적 자산만 공개
              .requestMatchers("/login.html", "/signup.html","/css/**", "/js/**", "/images/**").permitAll()
              // 로그아웃, 상태조회 API 공개
                  .requestMatchers("/api/auth/logout", "/api/auth/status", "/api/auth/signup").permitAll()
              // 그 외 API(로그인 처리 포함)는 인증된 사용자만
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
              .loginPage("/login.html")
              .loginProcessingUrl("/api/auth/login")
              .usernameParameter("email")
              .passwordParameter("password")
              .defaultSuccessUrl("/plan/planList.html", true)
              .failureUrl("/login.html?error")
          )
          .logout(logout -> logout
              .logoutUrl("/api/auth/logout")
              .logoutSuccessUrl("/login.html")
              .invalidateHttpSession(true)
              .deleteCookies("JSESSIONID")
          );

        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //  패스워드 인코더 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
