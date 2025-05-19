package com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto;

import com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.header.FoodResponseHeaderDto;
import com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.body.FoodResponseBodyDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodResponseDto {
    private FoodResponseHeaderDto header;
    private FoodResponseBodyDto body;  // 식품 정보 응답의 body 매핑
}
