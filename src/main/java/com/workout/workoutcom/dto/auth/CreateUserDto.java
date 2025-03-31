package com.workout.workoutcom.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Schema(description = "유저 정보를 담는 클래스")
@AllArgsConstructor
public class CreateUserDto implements UserDetails {

    @Schema(description = "유저 아이디", example = "qwer123")
    private String account;

    @Schema(description = "암호화된 패스워드", example = "1111")
    private String encryptPwd;

    @Schema(description ="유저 이름",example = "정대훈")
    private String name;

    @Schema(description ="유저 이름",example = "wjdeogns1057@naver.com")
    private String email;

    @Schema(description = "유저 휴대폰 번호", example = "01094743910")
    private String ph;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
