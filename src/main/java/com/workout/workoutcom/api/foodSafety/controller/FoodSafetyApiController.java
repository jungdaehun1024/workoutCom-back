package com.workout.workoutcom.api.foodSafety.controller;

import com.workout.workoutcom.api.foodSafety.FoodSafetyApiClient;
import com.workout.workoutcom.api.foodSafety.dto.FoodInfoDto;
import com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.FoodResponseDto;
import com.workout.workoutcom.api.foodSafety.service.FoodSafetyService;
import com.workout.workoutcom.configuration.auth.PrincipalDetails;
import com.workout.workoutcom.dto.ApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodSafetyApiController {
    private final FoodSafetyApiClient foodSafetyApiClient;
    private final FoodSafetyService foodSafetyService;

    @Autowired
    public FoodSafetyApiController(FoodSafetyApiClient foodSafetyApiClient, FoodSafetyService foodSafetyService){
        this.foodSafetyApiClient = foodSafetyApiClient;
        this.foodSafetyService = foodSafetyService;
    }

    //음식 조회 요청
    @GetMapping("/search/foods")
    public ResponseEntity<ApiResponseDto> searchFoodsRequest(@RequestParam("foodName") String foodName){
        FoodResponseDto result = foodSafetyApiClient.getFoodInfo(foodName);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "음식 조회 성공",result);
        return ResponseEntity.ok(response);
    }

    //음식 저장 요청
    @PostMapping("/saveFoodRequest")
    public ResponseEntity<ApiResponseDto> saveFoodRequest(@RequestBody FoodInfoDto foodInfoDto, @AuthenticationPrincipal PrincipalDetails principal){
        foodInfoDto.setRecorderAccount(principal.getUsername());
        foodSafetyService.saveFood(foodInfoDto);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "식사 저장 성공",null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //금주 저장 식단 조회
    @GetMapping("/getMyDiet")
    public ResponseEntity<ApiResponseDto> getMyDietRequest(@AuthenticationPrincipal PrincipalDetails principal){
        List<FoodInfoDto> weeklyRecords = foodSafetyService.getWeeklyRecords(principal.getUsername());
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(),"조회 성공",weeklyRecords);
        return ResponseEntity.ok(response);
    }
}
