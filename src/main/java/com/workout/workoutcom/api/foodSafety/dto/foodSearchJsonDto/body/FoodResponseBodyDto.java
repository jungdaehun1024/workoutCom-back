package com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.body;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodResponseBodyDto {
    private List<FoodResponseItemsDto> items; // 검색어에 대한 음식(이름,영양성분...) 결과 객체
    private String pageNo; // 페이지 번호
    private String totalCount; // 전체 결과 수
    private String numOfRows; // 한 페이지 결과 수

}
