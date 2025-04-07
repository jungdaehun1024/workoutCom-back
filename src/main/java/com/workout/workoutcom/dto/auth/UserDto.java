package com.workout.workoutcom.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "유저 정보를 담는 클래스")
@AllArgsConstructor
public class UserDto {

    @Schema(description = "유저 아이디", example = "qwer123")
    private String account;

    @Schema(description = "패스워드", example = "1111")
    private String password;

    @Schema(description ="유저 이름",example = "정대훈")
    private String name;

    @Schema(description ="유저 이름",example = "wjdeogns1057@naver.com")
    private String email;

    @Schema(description = "유저 휴대폰 번호", example = "01094743910")
    private String ph;

    @Schema(description = "가입일", example = "01094743910")
    private String userCreatedAt;

    @Schema(description = "회원 정보 수정일", example = "01094743910")
    private String userUpdatedAt;
}
