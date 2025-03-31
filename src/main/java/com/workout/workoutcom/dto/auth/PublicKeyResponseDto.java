package com.workout.workoutcom.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PublicKeyResponseDto {
    private String publicKeyModules;
    private String publicKeyExponent;
}
