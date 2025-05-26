package com.workout.workoutcom.api.foodSafety.service;

import com.workout.workoutcom.api.foodSafety.dao.FoodSafetyMapper;
import com.workout.workoutcom.api.foodSafety.dto.FoodInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodSafetyService {
    private final FoodSafetyMapper foodSafetyMapper;

    @Autowired
    public FoodSafetyService(FoodSafetyMapper foodSafetyMapper) {
        this.foodSafetyMapper = foodSafetyMapper;
    }

    public void saveFood(FoodInfoDto foodInfoDto){
        int result = foodSafetyMapper.insertFoodRecored(foodInfoDto);
        if(result != 1){
            throw new RuntimeException("음식 저장 중 에러가 발생했습니다.");
        }
    }

    public List<FoodInfoDto> getWeeklyRecords(String recorderAccount){
        List<FoodInfoDto> WeeklyRecords = foodSafetyMapper.selectWeeklyRecord(recorderAccount);
        if(WeeklyRecords == null){
            return null;
        }

        return WeeklyRecords;
    }

}
