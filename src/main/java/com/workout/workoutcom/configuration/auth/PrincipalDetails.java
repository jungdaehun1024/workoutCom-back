package com.workout.workoutcom.configuration.auth;

import com.workout.workoutcom.dto.auth.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
public class PrincipalDetails implements UserDetails {
    private final UserDto user;


    public PrincipalDetails(UserDto user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한이 필요하면 추가
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겨있는지 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부
        return true;
    }
}
