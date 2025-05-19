package com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.header;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodResponseHeaderDto {
    private String resultCode; // 결과코드
    private String resultMsg; // 결과 메세지
}
