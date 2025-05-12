package com.workout.workoutcom.dao.user;

import com.workout.workoutcom.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void registerUser(UserDto user);
    Optional<UserDto> getUserInfoByAccount(String account);
//    UserDto getUserInfoByAccount
    int updateUserPassword(UserDto user);

}
