package com.workout.workoutcom.controller.user;

import com.workout.workoutcom.api.foodSafety.FoodSafetyApiClient;
import com.workout.workoutcom.api.foodSafety.dto.foodSearchJsonDto.FoodResponseDto;
import com.workout.workoutcom.configuration.auth.PrincipalDetails;
import com.workout.workoutcom.dto.ApiResponseDto;
import com.workout.workoutcom.dto.user.PasswordDto;
import com.workout.workoutcom.dto.user.UserDto;
import com.workout.workoutcom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserCotroller {

    private final UserService userService;
    private final FoodSafetyApiClient foodSafetyApiClient;

    @Autowired
    public UserCotroller(UserService userService, FoodSafetyApiClient foodSafetyApiClient) {
        this.userService = userService;
        this.foodSafetyApiClient = foodSafetyApiClient;
    }

    //마이페이지 정보 조회
    @GetMapping("/getUserInfo")
    public ResponseEntity<ApiResponseDto> getUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        UserDto result =userService.findUserInfoByUsername(principalDetails);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(),"회원정보 조회 성공", result );
        return ResponseEntity.ok(response);
    }

    //패스워드 변경
    @PostMapping("/changePwd")
    public ResponseEntity<ApiResponseDto> changePwd(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody PasswordDto passwordDto) throws Exception {
        userService.changePassword(principalDetails,passwordDto);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "회원 정보 변경 성공", null);
        return ResponseEntity.ok(response);
    }

    //음식 조회 요청
    @GetMapping("/search/foods")
    public ResponseEntity<ApiResponseDto> searchFoodsRequest(@RequestParam("foodName") String foodName){
        FoodResponseDto result = foodSafetyApiClient.getFoodInfo(foodName);


        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(), "음식 조회 성공",result);
        return ResponseEntity.ok(response);
    }
}
