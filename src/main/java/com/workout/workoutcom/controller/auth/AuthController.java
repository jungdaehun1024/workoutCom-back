package com.workout.workoutcom.controller.auth;

import com.workout.workoutcom.dto.ApiResponseDto;
import com.workout.workoutcom.dto.auth.CreateUserDto;
import com.workout.workoutcom.dto.auth.PublicKeyResponseDto;
import com.workout.workoutcom.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name ="AuthController" , description = "인증에 대한 API")
public class AuthController {

      private final AuthService authService;

      @Autowired
      public AuthController(AuthService authService) {
          this.authService = authService;
      }

//    @PostMapping("/signUp")
//    @Operation(summary = "회원가입", description = "회원가입 API")
//    @ApiResponses({
//            @ApiResponse(responseCode = "201" , description = "회원가입에 성공했습니다.",content = @Content(mediaType = "application/json",
//            schema = @Schema(implementation = ApiResponseDto.class),
//            examples = @ExampleObject(value="{\"status\": 201 , \"message\" : \" 회원가입에 성공했습니다.\"}"))),
//
//            @ApiResponse(responseCode = "500", description = "회원 가입 중 서버에러 발생",content = @Content(mediaType = "application/json",
//            schema = @Schema(implementation = ApiResponseDto.class),
//            examples = @ExampleObject(value="{\"status\": 500 , \"message\" : \"서버에서 예기치 못한 오류가 발생했습니다. \"}")
//            ))
//    })
//    public ResponseEntity<ApiResponseDto> signUp(@RequestBody CreateUserDto user) {
//        userService.signUp(user);
//        ApiResponseDto response = new ApiResponseDto(HttpStatus.CREATED.value(),"회원가입이 완료되었습니다.",null);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    //회원가입 , 로그인 폼 진입시 해당 API로 세션에 개인키,공개키 등록
    @GetMapping("/getPublicKeyModule")
    public ResponseEntity<ApiResponseDto> getPublicKeyModule(HttpServletRequest request)throws Exception {
        PublicKeyResponseDto result  = authService.keyGenerate(request.getSession());
        ApiResponseDto<PublicKeyResponseDto> response = new ApiResponseDto<PublicKeyResponseDto>(HttpStatus.OK.value(), "공개키,개인키를 성공적으로 가져왔습니다.",result);
        return ResponseEntity.ok(response);
    }


    //회원가입
    @PostMapping("/auth/registerUser")
    public void signUp (@RequestBody CreateUserDto user,HttpServletRequest request) throws Exception {
          authService.registerUser(user,request.getSession());
    }

}
