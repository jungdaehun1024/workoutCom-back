package com.workout.workoutcom.controller.auth;

import com.workout.workoutcom.dto.ApiResponseDto;
import com.workout.workoutcom.dto.user.UserDto;
import com.workout.workoutcom.dto.auth.PublicKeyResponseDto;
import com.workout.workoutcom.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
      private final AuthService authService;

      @Autowired
      public AuthController(AuthService authService) {
          this.authService = authService;
      }

    //회원가입 , 로그인 폼 진입시 해당 API로 세션에 개인키,공개키 등록
    @GetMapping("/getPublicKeyModule")
    public ResponseEntity<ApiResponseDto> getPublicKeyModule(HttpServletRequest request)throws Exception {
        PublicKeyResponseDto result  = authService.keyGenerate(request.getSession());
        ApiResponseDto<PublicKeyResponseDto> response = new ApiResponseDto<PublicKeyResponseDto>(HttpStatus.OK.value(), "공개키,개인키를 성공적으로 가져왔습니다.",result);
        return ResponseEntity.ok(response);
    }

    //회원가입
    @PostMapping("/auth/registerUser")
    public ResponseEntity<ApiResponseDto> signUp (@RequestBody UserDto user, HttpServletRequest request) throws Exception {
          authService.registerUser(user,request.getSession());
          ApiResponseDto response = new ApiResponseDto(HttpStatus.CREATED.value(), "회원가입이 완료되었습니다.",null);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //로그인
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponseDto> login (@RequestBody UserDto user, HttpServletResponse res) throws Exception {
          authService.loginUser(res,user);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(),"로그인이 완료되었습니다.",null);
        return ResponseEntity.ok(response);

    }

    //로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse res,HttpServletRequest request) throws Exception {
        authService.logout(res,request);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(),"로그아웃이 완료되었습니다.",null);
        return ResponseEntity.ok(response);
    }

    //로그인 여부 체크
    @GetMapping("/auth/loginCkeck")
    public ResponseEntity<ApiResponseDto> loginCheck(HttpServletRequest request) throws Exception {
        boolean result =  authService.loginCheck(request);
        ApiResponseDto response = new ApiResponseDto(HttpStatus.OK.value(),"로그인 체크 완료되었습니다.",result);
        return ResponseEntity.ok(response);
    }
}
