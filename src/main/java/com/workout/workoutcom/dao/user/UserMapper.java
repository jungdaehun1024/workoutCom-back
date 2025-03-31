package com.workout.workoutcom.dao.user;

import com.workout.workoutcom.dto.auth.CreateUserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(CreateUserDto user);

}
