package com.workout.workoutcom.exception;

import com.workout.workoutcom.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //공통 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGeneralException(Exception exception){
        ApiResponseDto response = new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버에서 예기치 못한 오류가 발생했습니다.",null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    //404 NotFound Exception
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleNotFoundException(NotFoundException exception) {
        ApiResponseDto response = new ApiResponseDto( HttpStatus.NOT_FOUND.value(), exception.getMessage(),null);
        //예외 메시지와 함께 404 상태코드 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
