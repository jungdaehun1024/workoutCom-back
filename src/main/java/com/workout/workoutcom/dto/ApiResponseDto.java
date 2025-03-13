package com.workout.workoutcom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "API 응답을 담는 DTO클래스")
public class ApiResponseDto<T> {

    @Schema(description = "상태 코드")
    private int status; // 상태코드

    @Schema(description = "응답 메시지")
    private String message; // 에러 메시지

    @Schema(description = "응답 데이터")
    private T data ;
}
