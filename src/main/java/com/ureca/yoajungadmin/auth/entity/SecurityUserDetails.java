package com.ureca.yoajungadmin.auth.entity;

import com.ureca.yoajungadmin.user.entity.Role;
import com.ureca.yoajungadmin.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class SecurityUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Role role;

    public SecurityUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override public boolean isAccountNonExpired()  { return true; }
    // 계정 잠금 여부 반환
    @Override public boolean isAccountNonLocked()   { return true; }
    // 패스워드 만료 여부 반환
    @Override public boolean isCredentialsNonExpired() { return true; }
    // 계정 사용 가능 여부 반환
    @Override public boolean isEnabled()            { return true; }
}