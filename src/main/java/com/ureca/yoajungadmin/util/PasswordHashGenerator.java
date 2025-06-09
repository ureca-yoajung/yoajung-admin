package com.ureca.yoajungadmin.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";  // 원하시는 테스트용 비밀번호
        String hashed = encoder.encode(rawPassword);
        System.out.println("Encrypted password: " + hashed);
    }
}