package com.workout.workoutcom.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String account;
    private String password;
    private String name;
    private String email;
    private String ph;
    private String userCreatedAt;
    private String userUpdatedAt;
}
