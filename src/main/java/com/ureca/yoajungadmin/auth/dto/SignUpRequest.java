package com.ureca.yoajungadmin.auth.dto;

import com.ureca.yoajungadmin.user.entity.AgeGroup;
import com.ureca.yoajungadmin.user.entity.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private AgeGroup ageGroup;
}