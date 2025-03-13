package com.workout.workoutcom.exception;

public class NotFoundException extends BaseException {

    //메세지만 받는 생성자
    public NotFoundException(String message) {
        super(message);
    }

    //메세지와 원인(Throwable)을 모두 받을 수 있는 생성자
//    public BoardNotFoundException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
