package com.workout.workoutcom.api.foodSafety.dao;

import com.workout.workoutcom.api.foodSafety.dto.FoodInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodSafetyMapper {
    int insertFoodRecored(FoodInfoDto foodInfoDto);
    List<FoodInfoDto> selectFoodRecord(String recorderAccount,String specificDate);

}
