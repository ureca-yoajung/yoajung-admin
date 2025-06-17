package com.ureca.yoajungadmin.auth.service;

import com.ureca.yoajungadmin.auth.dto.SignUpRequest;
import com.ureca.yoajungadmin.plan.entity.Plan;
import com.ureca.yoajungadmin.plan.repository.PlanRepository;
import com.ureca.yoajungadmin.user.entity.Role;
import com.ureca.yoajungadmin.user.entity.User;
import com.ureca.yoajungadmin.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signup(SignUpRequest req) {

        // 이메일 중복 검사
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 요금제 존재 여부 확인
        Plan plan = planRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("ID=1 요금제가 존재하지 않습니다."));


        // User 엔티티 생성·저장
        User user = User.builder()
                .plan(plan)
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .gender(req.getGender())
                .ageGroup(req.getAgeGroup())
                .familyCount(1)
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);
    }


}