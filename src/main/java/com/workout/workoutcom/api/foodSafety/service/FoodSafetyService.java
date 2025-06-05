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

    // 검색한 음식 저장
    public void saveFood(FoodInfoDto foodInfoDto){
        int result = foodSafetyMapper.insertFoodRecord(foodInfoDto);
        if(result != 1){
            throw new RuntimeException("식사 저장 중 에러가 발생했습니다.");
        }
    }

    // 특정 날짜의 전체 식단 조회
    public List<FoodInfoDto> getWeeklyRecords(String recorderAccount,String specificDate){
        List<FoodInfoDto> weeklyRecords = foodSafetyMapper.selectFoodRecord(recorderAccount,specificDate);
        if(weeklyRecords == null){
            throw new RuntimeException("식사를 불러오는데 실패했습니다.");
        }
        return weeklyRecords;
    }

    // 기록한 음식 정보 상세
    public FoodInfoDto getFoodDetail(int foodRecordId){
        FoodInfoDto foodDetail = foodSafetyMapper.selectFoodDetail(foodRecordId);
        if(foodDetail == null){
            throw new RuntimeException("음식 상세정보를 불러오는데 실패했습니다.");
        }
        return foodDetail;
    }

    // 기록한 음식 정보 수정
    public void updateFoodDetail(FoodInfoDto foodInfoDto){
        int result = foodSafetyMapper.updateFoodDetail(foodInfoDto);
        if(result == 0) {
            throw new RuntimeException("식사 정보 수정에 실패했습니다.");
        }
    }

    // 기록한 음식 정보 삭제
    public void deleteFoodDetail(int foodRecordId){
        int result = foodSafetyMapper.deleteFoodDetail(foodRecordId);
        if(result == 0) {
            throw new RuntimeException("식사 정보 삭제에 실패했습니다.");
        }
    }
}
